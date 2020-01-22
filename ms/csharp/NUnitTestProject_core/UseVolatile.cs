using System;
using System.Threading;

namespace NUnitTestProject_core
{
    class Volatile
    {
        public static volatile int count = 0;
        public static void Method()
        {
            Console.WriteLine("s");
            while (count < 1000)
            {
                Thread.Sleep(10);
                ++count;
            }
            Console.WriteLine("e");
        }

        public static void Test(string[] args)
        {
            new Thread(Method).Start();
            Thread.Sleep(4000);
            count = 1000;
            Console.ReadLine();
        }
    }
}
