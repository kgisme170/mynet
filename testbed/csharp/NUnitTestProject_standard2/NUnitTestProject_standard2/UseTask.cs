using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace NUnitTestProject_standard2
{
    public partial class UseTask
    {
        [SetUp]
        public void Setup()
        {
        }

        public static Task<int> GetIntAsync()
        {
            TaskScheduler uiScheduler = TaskScheduler.FromCurrentSynchronizationContext();
            return Task<int>.Factory.StartNew(() =>
            {
                return 1;
            },
            CancellationToken.None,
            TaskCreationOptions.None,
            uiScheduler);
        }

        public static Task<IList<int>> GetListIntAsync()
        {
            return Task<IList<int>>.Factory.StartNew(() =>
            {
                return new List<int>() { 1, 3, 5 };
            },
            CancellationToken.None,
            TaskCreationOptions.None,
            TaskScheduler.Default);
        }

        private static Task<decimal> LongRunningCancellableOperation(int loop, CancellationToken cancellationToken)
        {
            Task<decimal> task = null;

            // Start a task and return it
            task = Task.Run(() =>
            {
                decimal result = 0;

                // Loop for a defined number of iterations
                for (int i = 0; i < loop; i++)
                {
                    // Check if a cancellation is requested, if yes,
                    // throw a TaskCanceledException.

                    if (cancellationToken.IsCancellationRequested)
                        throw new TaskCanceledException(task);

                    // Do something that takes times like a Thread.Sleep in .NET Core 2.
                    Thread.Sleep(10);
                    result += i;
                }

                return result;
            });

            return task;
        }

        public static async Task ExecuteTaskWithTimeoutAsync(TimeSpan timeSpan)
        {
            Console.WriteLine(nameof(ExecuteTaskWithTimeoutAsync));

            using (var cancellationTokenSource = new CancellationTokenSource(timeSpan))
            {
                try
                {
                    var result = await LongRunningCancellableOperation(500, cancellationTokenSource.Token);
                    Console.WriteLine("Result {0}", result);
                }
                catch (TaskCanceledException)
                {
                    Console.WriteLine("Task was cancelled");
                }
            }
            Console.WriteLine("Press enter to continue");
            Console.ReadLine();
        }

        static T Swap<T>(ref T lhs, ref T rhs)
        {
            T temp;
            temp = lhs;
            lhs = rhs;
            rhs = temp;
            return lhs;
        }

        [Test]
        public void Test1()
        {
            // Assert.Pass();
            var task = Task<int>.Factory.StartNew(() =>
            {
                return 1;
            });
            System.Diagnostics.Debug.WriteLine(task.Result);

            var task01 = Task<IReadOnlyList<int>>.Factory.StartNew(() =>
            {
                return new List<int>() { 4, 5, 11 };
            });

            System.Diagnostics.Debug.WriteLine(task01.Result.Count);

            Console.WriteLine(nameof(Test1));

            Task t = ExecuteTaskWithTimeoutAsync(new TimeSpan(0, 0, 1)); // 1 seconds
            t.Wait();
        }

        public async static void RunTest2()
        {
            var t = GetIntAsync();
            Console.WriteLine(t.Result);

            IList<int> x = await GetListIntAsync();
            foreach (var i in x)
            {
                Console.WriteLine(i);
            }
        }

        [Test]
        public void Test2()
        {
            TestDoubleTask();
        }

        public static async Task<int> CalcAsync(int wait, int x)
        {
            await Task.Run(() => Thread.Sleep(wait));
            Console.WriteLine("finish {0}", wait);
            return x;
        }

        public static async void TestDoubleTask()
        {
            Task<int> t1 = CalcAsync(1000, 1);
            Task<int> t2 = CalcAsync(2000, 2);
            int r1 = await t1;
            int r2 = await t2;
            Console.WriteLine(r1);
            Console.WriteLine(r2);
        }
    }
}