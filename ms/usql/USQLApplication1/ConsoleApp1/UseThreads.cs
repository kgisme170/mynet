using System;
using System.Threading;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class UseThreads
    {
        public static void Method()
        {
            Thread.Sleep(50);
        }
        public static async void Method2()
        {
            await Task.Run(new Action(LongTask));
            Console.WriteLine("Method2");
        }
        public static void LongTask()
        {
            Thread.Sleep(4000);
        }
        public static void Test(string[] args)
        {
            var t1 = new Thread(Method)
            {
                Name = "one"
            };
            t1.Start();
            Method2();
            Console.WriteLine("Main");
        }
    }
}
