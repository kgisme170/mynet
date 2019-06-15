using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace StreamInsightApp2
{
    class TollReading
    {
        public int TollId { get; set; }
        public int Number { get; set; }
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
        static PointEvent<TollReading> Gen1(
            int year, int month, int day, int hour, int minute, int second,
            int tollId, int number)
        {
            return PointEvent.CreateInsert(
                new DateTime(year, month, day, hour, minute, second),
                GenTollReading(tollId, number));
        }

        static PointEvent<TollReading> Gen2(
            int hour, int minute, int second,
            int tollId, int number)
        {
            return PointEvent.CreateInsert(
                new DateTime(2019, 06, 01, hour, minute, second),
                GenTollReading(tollId, number));
        }

        static IntervalEvent<TollReading> Gen3(
            int hourBegin, int minuteBegin, int secondBegin,
            int hourEnd, int minuteEnd, int secondEnd,
            int tollId, int number)
        {
            return IntervalEvent.CreateInsert(
                new DateTime(2019, 06, 01, hourBegin, minuteBegin, secondBegin),
                new DateTime(2019, 06, 01, hourEnd, minuteEnd, secondEnd),
                GenTollReading(tollId, number));
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
            //var streamable = intervals.ToIntervalStreamable(e => e, AdvanceTimeSettings.IncreasingStartTime);

            application.Delete();
            server.Dispose();
        }
    }
}