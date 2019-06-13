using System;

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

            //Console.WriteLine("/action/0?ti=5710001&Ver=2&mid=e1ebaa37-d158-844f-f1c3-15d604368bb2&gv=2&ec=payment&ea=order-confirmation&evt=custom&msclkid=dc65bba3ad5c180f6d49e74f7cc391e2-0&rn=758459");
            //Console.WriteLine(@"/action/0?ti=5710001&Ver=2&mid=e1ebaa37-d158-844f-f1c3-15d604368bb2&gv=2&ec=payment&ea=order-confirmation&evt=custom&msclkid=dc65bba3ad5c180f6d49e74f7cc391e2-0&rn=758459");

            string WithinDateString =
                String.Format("date={0:yyyy-MM-dd}&hour={1}...{2}&sparsestreamset=true", EndDateUTC, 0, 23);
            Console.WriteLine(WithinDateString);

            DateTimeOffset dateOffset1 = DateTimeOffset.Now;
            DateTimeOffset dateOffset2 = DateTimeOffset.UtcNow;
            TimeSpan difference = dateOffset1 - dateOffset2;
            Console.WriteLine("{0} - {1} = {2}",
                              dateOffset1, dateOffset2, difference);
        }
    }
}
