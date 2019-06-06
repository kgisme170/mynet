using System;
using System.Threading;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class Async
    {
        public static void LongTask()
        {
            Thread.Sleep(4000);
            Console.WriteLine("LongTask ends");
        }
        public static async void Method1()
        {
            await Task.Run(new Action(LongTask));
            Console.WriteLine("end");
        }
        public static async void Method2()
        {
            await Task.Delay(4000);
            Console.WriteLine("end");
        }
        public static async void Method3()
        {
            await Task.Delay(4000);
            Console.WriteLine("end");
        }
        public static void Test()
        {
            Method1();
            Method2();
            Method3();
            string str = Console.ReadLine();
            Console.WriteLine(str);
        }
    }
}
