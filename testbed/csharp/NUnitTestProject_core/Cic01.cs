using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;
using System.Threading.Tasks;

namespace NUnitTestProject_core
{
    class Cic01
    {
        static async Task DoSomethingAsync()
        {
            int val = 13;
            await Task.Delay(TimeSpan.FromSeconds(1));
            val *= 2;
            await Task.Delay(TimeSpan.FromSeconds(1));
            Trace.WriteLine(val);
        }
        [Test]
        public static void Test()
        {
            var t = DoSomethingAsync();
            t.Wait();
        }
    }
}
