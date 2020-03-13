using System;
using System.Collections.Generic;
using System.Reactive.Linq;
using System.Reactive.Threading.Tasks;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class UseSchedulerAndDefer
    {
        public static void Main(string[] args)
        {
        }
        /*
        int _v0;
        readonly int Myv0 = _v0++;

        int _v1;
        readonly Lazy<int> Myv1 = new Lazy<int>(() => _v1++);
        void UseV1()
        {
            Console.WriteLine(Myv1.Value);
        }

        int _v2;
        readonly Lazy<Task<int>> Myv2 = new Lazy<Task<int>>(async () =>
        {
            await Task.Delay(2).ConfigureAwait(false);
            return _v2++;
        });

        async Task UseV2()
        {
            Console.WriteLine(await Myv2.Value);
        }
        */
        static async Task<int> GetValueAsync()
        {
            await Task.Delay(3333);
            Console.WriteLine("GetValueAsync");//TODO: ___FILE__?
            return 3;
        }

        static void UseDefer()
        {
            var ob = Observable.Defer(
                () => GetValueAsync().ToObservable());
            ob.Subscribe(_ => { });
            ob.Subscribe(_ => { });
        }

        static void Test1(IEnumerable<int> input)
        {
            var schedulerPair = new ConcurrentExclusiveSchedulerPair(TaskScheduler.Default, 8);
            var concurrent = schedulerPair.ConcurrentScheduler;
            var exclusive = schedulerPair.ExclusiveScheduler;

            var parallelOpt = new ParallelOptions { TaskScheduler = concurrent };
            Parallel.ForEach(input, parallelOpt, x => Console.WriteLine(x));

            var opt = new ExecutionDataflowBlockOptions
            {
                TaskScheduler = concurrent,
            };
            var displayBlock = new ActionBlock<int>
            (
                r => Console.WriteLine(r),
                opt
            );
        }
    }
}
