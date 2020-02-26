using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace NUnitTestProject_standard2
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
        }

        public Task<int> GetIntAsync()
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

        public Task<IList<int>> GetListIntAsync()
        {
            return Task<IList<int>>.Factory.StartNew(() =>
            {
                return new List<int>();
            },
            CancellationToken.None,
            TaskCreationOptions.None,
            TaskScheduler.Default);
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
        }
    }
}