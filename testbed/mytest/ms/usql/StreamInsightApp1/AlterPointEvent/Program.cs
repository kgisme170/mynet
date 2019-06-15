using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reactive.Concurrency;
using System.Reactive.Linq;

namespace AlterPointEvent
{
    public class SensorReading
    {
        public int Time { get; set; }
        public int Value { get; set; }
        public override string ToString()
        {
            return new { Time, Value }.ToString();
        }
    }
    class Program
    {
        private static readonly SensorReading[] HistoricData = new SensorReading[]
        {
            new SensorReading { Time = 1, Value = 0 },
            new SensorReading { Time = 2, Value = 20 },
            new SensorReading { Time = 3, Value = 15 },
            new SensorReading { Time = 4, Value = 30 },
            new SensorReading { Time = 5, Value = 45 }, // here we crossed the threshold upward
            new SensorReading { Time = 6, Value = 50 },
            new SensorReading { Time = 7, Value = 30 }, // here we crossed downward // **** note that the current query logic only detects upward swings. ****/
            new SensorReading { Time = 8, Value = 35 },
            new SensorReading { Time = 9, Value = 60 }, // here we crossed upward again
            new SensorReading { Time = 10, Value = 20 }
        };

        private static IObservable<T> ToObservableInterval<T>(IEnumerable<T> source, TimeSpan period, IScheduler scheduler)
        {
            return Observable.Using(
                () => source.GetEnumerator(),
                it => Observable.Generate(
                    default(object),
                    _ => it.MoveNext(),
                    _ => _,
                    _ =>
                    {
                        Console.WriteLine("Input {0}", it.Current);
                        return it.Current;
                    },
                    _ => period, scheduler));
        }
        private static IObservable<SensorReading> SimulateLiveData()
        {
            return ToObservableInterval(HistoricData, TimeSpan.FromMilliseconds(500), Scheduler.ThreadPool);
        }
        
        static void Main(string[] args)
        {
            Server server = Server.Create("MyInstance");
            Application application = server.CreateApplication("app");
            Console.WriteLine("server+application ready");
            DateTime startTime = new DateTime(2019, 1, 1);
            IQStreamable<SensorReading> inputStream = 
                application.DefineObservable(() => SimulateLiveData()).ToPointStreamable(
                    r => PointEvent<SensorReading>.CreateInsert(startTime.AddSeconds(r.Time), r),
                    AdvanceTimeSettings.StrictlyIncreasingStartTime);
            int threshold = 42;

            // Alter all events 1 sec in the future.
            var alteredForward = inputStream.AlterEventStartTime(s => s.StartTime.AddSeconds(1));
            Console.WriteLine("stream ready");

            // Compare each event that occurs at input with the previous event.
            // Note that, this one works for strictly ordered, strictly (e.g 1 sec) regular streams.
            var crossedThreshold = from evt in inputStream
                                   from prev in alteredForward
                                   where prev.Value < threshold && evt.Value > threshold
                                   select new
                                   {
                                       Time = evt.Time,
                                       Low = prev.Value,
                                       High = evt.Value
                                   };
            Console.WriteLine("query ready");

            foreach (var outputSample in crossedThreshold.ToEnumerable())
            {
                Console.WriteLine(outputSample);
            }

            Console.WriteLine("Done. Press ENTER to terminate");

            application.Delete();
            server.Dispose();
        }
    }
}
