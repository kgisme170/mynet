using System;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.VisualStudio.Threading;

namespace UseNetStandard2_task
{
    class Program
    {
        private static async Task<int> F1(int wait = 1)
        {
            await Task.Run(() => Thread.Sleep(wait));
            Console.WriteLine("finish {0}", wait);
            return 1;
        }

        public static async Task Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            var task = F1();
            int f1 = await task;
            Console.WriteLine(f1);
        }
    }
}
