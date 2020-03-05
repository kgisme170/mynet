using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace TaskMain
{
    class Program
    {
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

        public static void Test01()
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
        }

        public static void Main(string[] args)
        {
            Task<int> primeNumberTask = Task.Run(() =>
                Enumerable.Range(2, 3000000).Count(
                    n => Enumerable.Range(2, (int)Math.Sqrt(n) - 1).All(i => n % i > 0)));

            var awaiter = primeNumberTask.GetAwaiter();
            awaiter.OnCompleted(() =>
            {
                int result = awaiter.GetResult();
                Console.WriteLine(result);
            });

            Task<int> primeNumberTask2 = Task.Run(() =>
                Enumerable.Range(2, 3000000).Count(
                    n => Enumerable.Range(2, (int)Math.Sqrt(n) - 1).All(i => n % i > 0)));

            primeNumberTask2.ContinueWith(antecedent =>
            {
                int result = antecedent.Result;
                Console.WriteLine(result); // Writes 123
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
