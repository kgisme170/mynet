using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace NUnitTestProject_standard2
{
    public partial class UseTask
    {
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
            var task01 = Task<IReadOnlyList<int>>.Factory.StartNew(() =>
            {
                return new List<int>() { 4, 5, 11 };
            });
            task01.Wait();

            System.Diagnostics.Debug.WriteLine(task01.Result.Count);
            Assert.Equals(3, task01.Result.Count);
            Console.WriteLine(nameof(Test1));
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