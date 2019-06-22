using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Collections.Generic;
using System.Reactive;
using System.Reactive.Linq;
using System.Threading;

namespace StreamInsightApp2
{
    class TollReading
    {
        public int TollId { get; set; }
        public int Number { get; set; }
        public override string ToString()
        {
            return new { TollId, Number }.ToString();
        }
        public TollReading() { }
    }
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
    class TollInterval
    {
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
        public TollReading Payload { get; set; }
        public TollInterval() { }
    }
    class Program
    {
        static Server server = Server.Create("MyInstance");
        static Application application = server.CreateApplication("Myapp");

        static TollReading GenTollReading(int tollId, int number)
        {
            return new TollReading()
            {
                TollId = tollId,
                Number = number
            };
        }
        static TollPoint Gen1(
            int year, int month, int day, int hour, int minute, int second,
            int tollId, int number)
        {
            return new TollPoint()
            {
                Year = year,
                Month = month,
                Day = day,
                Hour = hour,
                Minute = minute,
                Second = second,
                TollId = tollId,
                Number = number
            };
        }

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

        static TollInterval Gen3(
            int hourBegin, int minuteBegin, int secondBegin,
            int hourEnd, int minuteEnd, int secondEnd,
            int tollId, int number)
        {
            return new TollInterval()
            {
                StartTime = new DateTime(2019, 06, 01, hourBegin, minuteBegin, secondBegin),
                EndTime = new DateTime(2019, 06, 01, hourEnd, minuteEnd, secondEnd),
                Payload = GenTollReading(tollId, number)
            };
        }

        public static IEnumerable<int> Gen()
        {
            int i = 0;
            while (i<=10)
            {
                Thread.Sleep(300);
                yield return i++;
            }
        }
        public static void ConsoleWritePointNoCTI<T>(PointEvent<T> e)
        {
            // Exclude EventKind.Cti
            if (e.EventKind == EventKind.Insert)
            {
                Console.WriteLine("INSERT <{0}> {1}",
                    e.StartTime.DateTime, e.Payload.ToString());
            }
        }

        public static void Main(string[] args)
        {
            Console.WriteLine("Begin");

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

            var intervals = new[]
            {
                Gen3(12, 01, 00, 12, 03, 00, 1, 1),
                Gen3(12, 02, 00, 12, 03, 00, 1, 2),
                Gen3(12, 03, 00, 12, 08, 00, 1, 1),
                Gen3(12, 07, 00, 12, 10, 00, 1, 2),
                Gen3(12, 10, 00, 12, 14, 00, 1, 3),
                Gen3(12, 11, 00, 12, 13, 00, 1, 1),
                Gen3(12, 20, 00, 12, 22, 00, 1, 2),
                Gen3(12, 22, 00, 12, 25, 00, 1, 2),
            };
            /*
            IQueryable iq = application.DefineEnumerable(()=>intervals);
            IEnumerable<int> ii = Gen();
            foreach(int i in Gen())
            {
                Console.WriteLine(i);
            }
            foreach (int i in Gen())
            {
                Console.WriteLine(i);
            }*/

            //points.
            var obPoints = points.ToObservable();
            var pointObservable = application.DefineObservable(() => obPoints);
            var pointStreamable = pointObservable.ToPointStreamable(
                e => PointEvent<TollReading>.CreateInsert(
                    new DateTime(e.Year, e.Month, e.Day, e.Hour, e.Minute, e.Second, DateTimeKind.Utc),
                    new TollReading()
                    {
                        TollId = e.TollId,
                        Number = e.Number
                    }
                ),
                AdvanceTimeSettings.StrictlyIncreasingStartTime);

            var consoleObserver = application.DefineObserver(() => Observer.Create<PointEvent<TollReading>>(ConsoleWritePointNoCTI));
            var binding = pointStreamable.Bind(consoleObserver);
            using (binding.Run())
            {
                Console.ReadLine();
            }

            //var alterForward = pointStreamable.AlterEventStartTime(e => e.StartTime.AddSeconds(3));
            //Will throw exceptions for data contracts.
            /*
            foreach (var a in pointStreamable.ToEnumerable())
            {
                Console.WriteLine(a);
            }
            */

            //intervals
            var obIntervals = intervals.ToObservable();
            var intervalObservable = application.DefineObservable(() => obIntervals);
            var intervalStreamable = intervalObservable.ToIntervalStreamable(
                e => IntervalEvent<TollReading>.CreateInsert(e.StartTime, e.EndTime, e.Payload));

            application.Delete();
            server.Dispose();
        }
    }
}