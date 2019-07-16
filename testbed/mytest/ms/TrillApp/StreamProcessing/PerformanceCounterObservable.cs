using Microsoft.StreamProcessing;
using System;
using System.Diagnostics;
using System.Linq;
using System.Reactive.Linq;
using System.Threading;
namespace StreamProcessing
{
    /// <summary>
    /// Struct that holds the actual performance counter
    /// </summary>
    public struct PerformanceCounterSample
    {
        public DateTime StartTime;
        public float Value;

        public override string ToString()
        {
            return new { StartTime, Value }.ToString();
        }
    }

    /// <summary>
    /// An observable source based on a local machine performance counter.
    /// </summary>
    public sealed class PerformanceCounterObservable : IObservable<PerformanceCounterSample>
    {
        readonly Func<PerformanceCounter> _createCounter;
        readonly TimeSpan _pollingInterval;

        public PerformanceCounterObservable(string categoryName, string counterName, string instanceName, TimeSpan pollingInterval)
        {
            // create a new performance counter for every subscription
            _createCounter = () => new PerformanceCounter(categoryName, counterName, instanceName, true);
            _pollingInterval = pollingInterval;
        }

        public IDisposable Subscribe(IObserver<PerformanceCounterSample> observer)
        {
            return new Subscription(this, observer);
        }

        sealed class Subscription : IDisposable
        {
            readonly PerformanceCounter _counter;
            readonly TimeSpan _pollingInterval;
            readonly IObserver<PerformanceCounterSample> _observer;
            readonly Timer _timer;
            readonly object _sync = new object();
            CounterSample _previousSample;
            bool _isDisposed;

            public Subscription(PerformanceCounterObservable observable, IObserver<PerformanceCounterSample> observer)
            {
                // create a new counter for this subscription
                _counter = observable._createCounter();
                _pollingInterval = observable._pollingInterval;
                _observer = observer;

                // seed previous sample to support computation
                _previousSample = _counter.NextSample();

                // create a timer to support polling counter at an interval
                _timer = new Timer(Sample);
                _timer.Change(_pollingInterval.Milliseconds, -1);
            }

            void Sample(object state)
            {
                lock (_sync)
                {
                    if (!_isDisposed)
                    {
                        DateTime startTime = DateTime.UtcNow;
                        CounterSample currentSample = _counter.NextSample();
                        float value = CounterSample.Calculate(_previousSample, currentSample);
                        _observer.OnNext(new PerformanceCounterSample { StartTime = startTime, Value = value });
                        _previousSample = currentSample;
                        _timer.Change(_pollingInterval.Milliseconds, -1);
                    }
                }
            }

            public void Dispose()
            {
                lock (_sync)
                {
                    if (!_isDisposed)
                    {
                        _isDisposed = true;
                        _timer.Dispose();
                        _counter.Dispose();
                    }
                }
            }
        }

        public static void Test()
        {
            // Poll performance counter 4 times a second
            TimeSpan pollingInterval = TimeSpan.FromSeconds(0.25);

            // Take the total processor utilization performance counter
            string categoryName = "Processor";
            string counterName = "% Processor Time";
            string instanceName = "_Total";

            // Create an observable that feeds the performance counter periodically
            IObservable<PerformanceCounterSample> source = new PerformanceCounterObservable(categoryName, counterName, instanceName, pollingInterval);

            // Load the observable as a stream in Trill
            var inputStream =
                source.Select(e => StreamEvent.CreateStart(e.StartTime.Ticks, e)) // Create an IObservable of StreamEvent<>
                .ToStreamable(/*OnCompletedPolicy.Flush(), PeriodicPunctuationPolicy.Count(4)*/); // Create a streamable with a punctuation every 4 events

            // The query
            long windowSize = TimeSpan.FromSeconds(2).Ticks;
            var query = inputStream.AlterEventDuration(windowSize).Average(e => e.Value);

            // Egress results and write to console
            query.ToStreamEventObservable().ForEachAsync(e => WriteEvent(e)).Wait();
        }

        static void WriteEvent<T>(StreamEvent<T> e)
        {
            if (e.IsInterval)
            {
                Console.WriteLine(
                    "Event Kind=Interval\tStart Time={0}\tEnd Time={1}\tPayload={2}",
                    e.StartTime, e.EndTime, e.Payload);
            }
            else if (e.IsStart)
            {
                Console.WriteLine(
                    "Event Kind=Start\tStart Time={0}\tEnd Time=????\tPayload={1}",
                    e.StartTime, e.Payload);
            }
            else if (e.IsEnd)
            {
                Console.WriteLine(
                    "Event Kind=End\t\tStart Time={0}\tEnd Time={1}\tPayload={2}",
                    e.StartTime, e.EndTime, e.Payload);
            }
            else // Is a punctuation
            {
                Console.WriteLine(
                  "Event Kind=Punctuation\tSync Time={0}",
                  e.StartTime);
            }
        }
    }
}
