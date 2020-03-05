using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

namespace UseNetStandard2_task
{
    class Program
    {

        public static async Task Main(string[] args)
        {
            await ProcessTaskAsync();
            // await Task.Delay(TimeSpan.FromSeconds(2));
        }

        static async Task<int> DelayAndReturnAsync(int val)
        {
            await Task.Delay(TimeSpan.FromSeconds(val));
            return val;
        }

        static async Task AwaitAndProcessAsync(Task<int> task)
        {
            var result = await task;
            Console.WriteLine(result);
        }

        static async Task ProcessTaskAsync()
        {
            Task<int> taskA = DelayAndReturnAsync(4);
            Task<int> taskB = DelayAndReturnAsync(6);
            Task<int> taskC = DelayAndReturnAsync(2);
            var tasks = new[] { taskA, taskB, taskC };

            var processingTasks = tasks.Select(async t =>
            {
                var result = await t;
                Console.WriteLine(result); // 按照完成的顺序
                return result;
            }).ToArray(); // or var processingTasks = (from t in tasks select AwaitAndProcessAsyc(t))
            Console.WriteLine("----------");
            var t1 = await Task.WhenAll(processingTasks);
            Console.WriteLine("----------");
            foreach (var i in t1)
            {
                // 按照声明的顺序
                Console.WriteLine(i);
            }

            var tuple = new Tuple<List<int>, List<int>>(new List<int>() { 1 }, new List<int>() { 2 });
        }
    }
}
