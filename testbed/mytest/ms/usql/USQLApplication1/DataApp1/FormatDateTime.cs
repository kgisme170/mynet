using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataApp1
{
    class FormatDateTime
    {
        public static void Test()
        {
            var v = "ab";
            dynamic d = "ab";
            Console.WriteLine(v.Length + d.Length);

            DateTime dt = DateTime.Now;
            Console.WriteLine(dt == null);

            DateTime StartDateUTC = DateTime.Parse("2019-05-28");
            StartDateUTC          = StartDateUTC.AddHours(Int32.Parse("06"));
            DateTime EndDateUTC   = StartDateUTC.AddHours(1);
            Console.WriteLine("EndDateUTC = " + EndDateUTC);
            string inputPath = "https://cosmos08.osdinfra.net/cosmos/sharedData.Ads.Dev";
            string s = string.Format("{0}/StreamingConversion/StreamingToBatch/{1:yyyy}/{1:MM}/{1:dd}/{1:HH}/ConversionWithoutAttribution_{1:yyyyMMdd_HH}00.ss",
                inputPath, EndDateUTC);
            Console.WriteLine(s);
        }
    }
}
