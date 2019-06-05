using System;
using System.Threading;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class Program
    {
        public static void LongTask()
        {
            Thread.Sleep(4000);
            Console.WriteLine("end");
        }
        public static async void Method1()
        {
            await Task.Run(new Action(LongTask));
        }
        public static async void Method2()
        {
            await Task.Run(new Action(LongTask));
        }
        public static async void Method3()
        {
            await Task.Run(new Action(LongTask));
        }
        public static void Main(string[] args)
        {
            Method1();
            Method2();
            Method3();
        }
    }
}
