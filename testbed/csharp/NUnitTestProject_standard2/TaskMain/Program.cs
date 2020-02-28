using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace TaskMain
{
    class Program
    {
        public static async Task<int> CalcAsync(int wait, int x)
        {
            await Task.Run(() => Thread.Sleep(wait));
            Console.WriteLine("finish {0}", wait);
            return x;
        }

        public static async Task<int> TestTrippleTask()
        {
            Task<int> t1 = CalcAsync(6000, 6);
            Task<int> t2 = CalcAsync(2000, 2);
            Task<int> t3 = CalcAsync(4000, 4);
            Console.WriteLine("------");
            int r1 = await t1;
            Console.WriteLine("r1 awaited");
            int r2 = await t2;
            Console.WriteLine("r2 awaited");
            int r3 = await t3;
            Console.WriteLine("r3 awaited");
            return r1 + r2 + r3;
        }

        public static void TestDoubleTask()
        {
            Task<int> t1 = CalcAsync(6000, 6);
            Task<int> t2 = CalcAsync(2000, 2);
            var tasks = new Task[] { t2, t1 };
            Task.WaitAll(tasks);
        }

        static Task<int> GetAnswerToLife()
        {
            var tcs = new TaskCompletionSource<int>();
            // Create a timer that fires once in 5000 ms:
            var timer = new System.Timers.Timer(5000) { AutoReset = false };
            timer.Elapsed += delegate { timer.Dispose(); tcs.SetResult(42); };
            timer.Start();
            return tcs.Task;
        }

        public static void Main(string[] args)
        {
            Task task = Task.Run(() =>
            {
                Thread.Sleep(2000);
                Console.WriteLine("Foo");
            });
            Console.WriteLine(task.IsCompleted); //False
            task.Wait();
            Console.WriteLine(task.IsCompleted); //True

            Task t = Task.Run(() => { throw null; });
            try
            {
                t.Wait();
            }
            catch (AggregateException aex)
            {
                if (aex.InnerException is NullReferenceException)
                    Console.WriteLine("Null!");
                else
                    throw;
            }

            Console.WriteLine("Main");
            TestDoubleTask();


            Task<int> primeNumberTask = Task.Run(() =>
                Enumerable.Range(2, 3000000).Count(
                    n => Enumerable.Range(2, (int)Math.Sqrt(n) - 1).All(i => n % i > 0)));

            //获取用于等待此 System.Threading.Tasks.Task<TResult>的等待者
            var awaiter = primeNumberTask.GetAwaiter();
            //将操作设置为当 System.Runtime.CompilerServices.TaskAwaiter<TResult> 对象停止等待异步任务完成时执行
            awaiter.OnCompleted(() =>
            {
                int result = awaiter.GetResult(); //异步任务完成后关闭等待任务
                Console.WriteLine(result);       //打印结果
            });
            var aw = GetAnswerToLife().GetAwaiter();
            aw.OnCompleted(() => Console.WriteLine(awaiter.GetResult()));

            Task<int> primeNumberTask2 = Task.Run(() =>
                Enumerable.Range(2, 3000000).Count(
                    n => Enumerable.Range(2, (int)Math.Sqrt(n) - 1).All(i => n % i > 0)));

            primeNumberTask2.ContinueWith(antecedent =>
            {
                int result = antecedent.Result;
                Console.WriteLine(result);          // Writes 123
            });
        }

        static Task<TResult> Run<TResult>(Func<TResult> function)
        {
            var tcs = new TaskCompletionSource<TResult>();
            new Thread(() =>
            {
                try { tcs.SetResult(function()); }
                catch (Exception ex) { tcs.SetException(ex); }
            }).Start();
            return tcs.Task;
        }

        public void UseCompletionSource()
        {
            Task<int> task = Run(() => { Thread.Sleep(5000); return 42; });
            Console.WriteLine(task.Result);
            Console.Read();
        }

        public static void Main2(string[] args)
        {
            var awaiter = GetAnswerToLife2().GetAwaiter();
            awaiter.OnCompleted(() => Console.WriteLine(awaiter.GetResult()));
        }

        static Task<int> GetAnswerToLife2()
        {
            var tcs = new TaskCompletionSource<int>();
            // Create a timer that fires once in 5000 ms:
            var timer = new System.Timers.Timer(5000) { AutoReset = false };
            timer.Elapsed += delegate { timer.Dispose(); tcs.SetResult(42); };
            timer.Start();
            return tcs.Task;
        }

        public static void UseParallel(IList<Task<int>> taskList)
        {
            while (true)
            {
                Parallel.ForEach(taskList, new Action<Task>(task =>
                {
                    //...
                }));
                Thread.Sleep(1000);
            }
        }

        private static async Task<string> TestWithResultAsync()
        {
            Console.WriteLine("1. 异步任务start……");
            await Task.Delay(2000);
            Console.WriteLine("2. 异步任务end……");
            return "2秒以后";
        }

        private void TaskCompleteSource()
        {
            var result = AwaitByTaskCompleteSource(TestWithResultAsync);
            Console.WriteLine($"4. TaskCompleteSource_OnClick end:{result}");
        }

        private string AwaitByTaskCompleteSource(Func<Task<string>> func)
        {
            var taskCompletionSource = new TaskCompletionSource<string>();
            var task1 = taskCompletionSource.Task;
            Task.Run(async () =>
            {
                var result = await func.Invoke();
                taskCompletionSource.SetResult(result);
            });
            var task1Result = task1.Result;
            Console.WriteLine($"3. AwaitByTaskCompleteSource end:{task1Result}");
            return task1Result;
        }
    }
}
