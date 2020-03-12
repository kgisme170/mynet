using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Globalization;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace TaskMain
{
    class UseAsync
    {
        private void flatternList()
        {
            var listOfList = new List<List<int>>()
            {
                new List<int>() {1, 2, 3},
                new List<int>() {4, 5},
                new List<int>() {10, 20},
            };
            var flattern1 = listOfList.SelectMany(x => x).ToList();
            Console.WriteLine(string.Join(",", flattern1));

            var flattern2 = from list in listOfList from item in list select item;
            Console.WriteLine(string.Join(",", flattern2));
        }

        [Test]
        public static Task F7(int i = 1)
        {
            if (i == 1)
            {
                return Task.CompletedTask;
            }
            return Task.Delay(1000);
        }

        [Test]
        public static void F8()
        {
            var cts = new CancellationTokenSource();
            var logTask = new Task(() => Thread.Sleep(3000), cts.Token);
            /*
            Console.WriteLine(logTask.Status);
            cts.Cancel();
            Console.WriteLine(logTask.Status);
            logTask.RunSynchronously();
            */
            logTask.Start();
            Thread.Sleep(1000);
            Console.WriteLine("After sleeping 1s");
            Console.WriteLine(logTask.Status);
            cts.Cancel();
            Thread.Sleep(1000);
            Console.WriteLine("After sleeping 1s");
            Console.WriteLine(logTask.Status);

            Thread.Sleep(3000);
            Console.WriteLine(logTask.Status);
            Console.WriteLine("After sleeping 3s");
        }

        interface IMy
        {
            Task<int> DosthAsync();
        }

        public class My : IMy
        {
            public Task<int> DosthAsync()
            {
                return Task.FromResult(1);
            }
        }

        [Test]
        public static Task<int> GetValueFromCache(int key)
        {
            if (key == 1)
            {
                return Task.FromResult(key);
            }
            return GetAsync(key);
        }

        public static async Task<int> GetAsync(int key)
        {
            await Task.Delay(0);
            return key;
        }

        [Test]
        public static async Task<int> F01(int i)
        {
            await Task.Delay(TimeSpan.FromSeconds(3));
            if (i == 1)
            {
                throw new Exception("ok");
            }
            await Task.Delay(TimeSpan.FromSeconds(3));
            return 2;
        }

        [Test]
        public static void UseBgWorker()
        {
            var bgworker = new BackgroundWorker()
            {
                WorkerSupportsCancellation = true
            };
            bgworker.DoWork += (object sender, DoWorkEventArgs e) =>
            {
                Console.WriteLine("Begin bgworker sleep {0}", DateTime.Now);
                Thread.Sleep(15000);
                Console.WriteLine("bgworker ends {0}", DateTime.Now);
            };
            bgworker.RunWorkerAsync();
            Console.WriteLine(bgworker.CancellationPending);
            Thread.Sleep(3000);
            Console.WriteLine("Call cancel {0}", DateTime.Now);
            Console.WriteLine(bgworker.CancellationPending);
            Console.WriteLine("-----------");
            bgworker.CancelAsync();
            Console.WriteLine(bgworker.CancellationPending);
            Console.WriteLine(bgworker.CancellationPending);
            Thread.Sleep(3000);
            Console.WriteLine(bgworker.CancellationPending);
            Console.WriteLine("main ends {0}", DateTime.Now);
        }

        public class CustomException : Exception
        {
            public CustomException(string message) : base(message)
            { }
        }
        
        [Test]
        public static void F2()
        {
            var task1 = Task.Factory.StartNew(() => {
                var child1 = Task.Factory.StartNew(() => {
                    var child2 = Task.Factory.StartNew(() => {
                        // This exception is nested inside three AggregateExceptions.
                        throw new CustomException("Attached child2 faulted.");
                    }, TaskCreationOptions.AttachedToParent);

                    // This exception is nested inside two AggregateExceptions.
                    throw new CustomException("Attached child1 faulted.");
                }, TaskCreationOptions.AttachedToParent);
            });

            try
            {
                task1.Wait();
            }
            catch (AggregateException ae)
            {
                foreach (var e in ae.Flatten().InnerExceptions)
                {
                    if (e is CustomException)
                    {
                        Console.WriteLine(e.Message);
                    }
                    else
                    {
                        throw;
                    }
                }
            }
        }

        [Test]
        public static void F3()
        {
            Console.WriteLine("hi");

            try
            {
                var task = F01(1);
                task.Wait();
            }
            /*
            catch (AggregateException e)
            {
                Console.WriteLine(e.Message);
            }*/
            catch (Exception e)
            {
                Console.WriteLine(e.GetType().Name);
                Console.WriteLine(e.Message);
            }
        }

        [Test]
        public static async Task<int> F4()
        {
            await Task.Delay(1000);
            return await Task<int>.Run(() =>
            {
                Console.WriteLine("");
                return 2;
            }); // CompletedTask;
        }

        public static async Task F5()
        {
            await Task.Delay(1000);
        }

        public static Task F6()
        {
            return Task.CompletedTask;
        }

        public static void TestNewTask()
        {
            var task = new Task(() => Thread.Sleep(TimeSpan.FromSeconds(3)));
            task.RunSynchronously();
            Task.WaitAll(task);
        }

        public static async Task TaskTest()
        {
            try
            {
                await Task.Delay(8000, tokenSource.Token);
            }
            catch (TaskCanceledException e)
            {
                Console.WriteLine("catch task cancellation {0}", e.Message);
            }

            Console.WriteLine("task done");
        }

        static CancellationTokenSource tokenSource = new CancellationTokenSource();

        [Test]
        public static void TestCancelTask()
        {
            _ = TaskTest();
            Console.WriteLine("Main not blocked");
            var input = Console.ReadLine();
            if (input == "s")
            {
                Console.WriteLine("Manual cancel begin");
                tokenSource.Cancel();
                Console.WriteLine("Manual cancel end");
            }
            Console.WriteLine("main done");
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

        [Test]
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

        [Test]
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

        [Test]
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

        [Test]
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

            Task t = Task.Run(() => { throw new Exception("Test01"); });
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

        [Test]
        public static void T()
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
        
        [Test]
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

        public static void UseParallel(IList<Task<int>> taskList, CancellationToken token)
        {
            while (true)
            {
                Parallel.ForEach(
                    taskList,
                    new ParallelOptions { CancellationToken = token },
                    new Action<Task>(task =>
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

        [Test]
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
