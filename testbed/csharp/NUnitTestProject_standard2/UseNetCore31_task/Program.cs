using Nito.AsyncEx;
using System;
using System.Linq;
using System.Reactive.Linq;
using System.Reactive.Threading.Tasks;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class Program
    {
        public static void Main(string[] args)
        {
        }

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

        static async Task<int> GetValueAsync()
        {
            await Task.Delay(3333);
            Console.WriteLine("GetValueAsync");//TODO: ___FILE__?
            return 3;
        }

        static void UseDefer()
        {
            var ob = Observable.Defer(
                () => GetValueAsync.ToObservable());
            ob.Subscribe(_ => { });
            ob.Subscribe(_ => { });
        }

        static void Test1(IEquatable<int> input)
        {
            var schedulerPair = new ConcurrentExclusiveSchedulerPair(TaskScheduler.Default, 8);
            var concurrent = schedulerPair.ConcurrentScheduler;
            var exclusive = schedulerPair.ExclusiveScheduler;

            var parallelOpt = new ParallelOptions { TaskScheduler = concurrent };
            Parallel.ForEach(input, parallelOpt,
                x => Console.WriteLine(x));

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

        static async Task Test()
        {
            IObservable<DateTimeOffset> timestamps =
                Observable.Interval(TimeSpan.FromSeconds(1))
                .Timestamp()
                .TakeUntil(Observable.Timer(TimeSpan.FromSeconds(100)))
                .Where(x => x.Value % 2 == 0)
                .TakeWhile(x => x.Value != 100)
                .Select(x => x.Timestamp);
            // OnComplete/OnError for Observable? TODO

            var cts = new CancellationTokenSource(TimeSpan.FromSeconds(5));
            var token = cts.Token;
            var first = await timestamps.TakeLast(1).ToTask(token);
        }
    }
}
