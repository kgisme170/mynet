using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;
using Microsoft.ComplexEventProcessing.Extensibility;
using Microsoft.ComplexEventProcessing.Serialization;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Reactive;
using System.Reactive.Linq;
using System.Threading;
namespace SIObserverApp
{
    class TollPoint
    {
        public int Year { get; set; }
        public int Month { get; set; }
        public int Day { get; set; }
        public int Hour { get; set; }
        public int Minute { get; set; }
        public int Second { get; set; }

        public int TollId { get; set; }
        public int Number { get; set; }
        public TollPoint() { }
    }
    class Observer : IObserver<TollPoint>
    {
        public void OnCompleted()
        {
            // Prevent failure if OnCompleted() is called several times by encapsulating it in a try-catch block
            try
            {
                // Retrieve wait handle and set signal
                var adapterStopSignal = EventWaitHandle.OpenExisting("StockInsightSignal");
                adapterStopSignal.Set();
            }
            catch
            {
            }
        }

        public void OnError(Exception error)
        {
            throw error;
        }

        public void OnNext(TollPoint value)
        {
            Console.WriteLine("Output: " +
                value.TollId + " " +
                value.Number);
        }
    }
    class Program
    {
        static TollPoint Gen2(
            int hour, int minute, int second,
            int tollId, int number)
        {
            return new TollPoint()
            {
                Year = 2019,
                Month = 06,
                Day = 01,
                Hour = hour,
                Minute = minute,
                Second = second,
                TollId = tollId,
                Number = number
            };
        }

        static void Main(string[] args)
        {
            Server server = Server.Create("MyInstance");
            Application application = server.CreateApplication("Myapp");
            TraceListener tracer = new ConsoleTraceListener();
            var points = new[]
            {
                Gen2(12, 01, 00, 1, 3),
                Gen2(12, 01, 01, 1, 1),
                Gen2(12, 01, 02, 2, 4),
                Gen2(12, 01, 02, 1, 3),
                Gen2(12, 01, 03, 2, 2),
                Gen2(12, 01, 03, 1, 2),
                Gen2(12, 01, 04, 1, 1),
                Gen2(12, 01, 05, 2, 5),
                Gen2(12, 01, 06, 2, 1),
                Gen2(12, 01, 07, 1, 3),
                Gen2(12, 01, 07, 2, 6),
            };
            
            var observable = points.ToObservable<TollPoint>();
            var cepStream = CepStream<TollPoint>.Create("s1");
            var filter = from e in cepStream
                         where e.Number > 3
                         select e;
            var observer = new Observer();
            var adapterStopSignal = new EventWaitHandle(false, EventResetMode.AutoReset, "StockInsightSignal");

            // This starts the query
            var toObservable = cepStream.ToObservable<TollPoint>();
            var disposable = toObservable.Subscribe(observer);

            // Wait for query to finish
            adapterStopSignal.WaitOne();

            application.Delete();
            server.Dispose();
            // Write a blank line
            Console.WriteLine();
        }
    }
}
