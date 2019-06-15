using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace HitchHikerGuide
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
        static PointEvent<TollReading> Gen1(int year, int month, int day, int tollId, int number)
        {
            return PointEvent.CreateInsert(new DateTime(year, month, day), GenTollReading(tollId, number));
        }
        public static void Main(string[] args)
        {
            Console.WriteLine("Begin");

            var rawData = new[] {
                Gen1(2019, 06, 01, 1, 3),
                Gen1(2019, 06, 02, 1, 4),
                Gen1(2019, 06, 03, 1, 4),
                Gen1(2019, 06, 03, 2, 1),
                Gen1(2019, 06, 04, 1, 2),
                Gen1(2019, 06, 05, 1, 9),
                Gen1(2019, 06, 06, 1, 2),
                Gen1(2019, 06, 06, 2, 8),
                Gen1(2019, 06, 07, 1, 7),
                Gen1(2019, 06, 08, 2, 5),
                Gen1(2019, 06, 09, 1, 6),
                Gen1(2019, 06, 10, 1, 8),
            };

            application.Delete();
            server.Dispose();
        }
    }
}
