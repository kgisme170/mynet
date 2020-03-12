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
        public AsyncLazy<int> Data { get; } = new AsyncLazy<int>(async () =>
                                             {
                                                 await Task.Delay(3000);
                                                 return 33;
                                             });

        private readonly SemaphoreSlim _mutex = new SemaphoreSlim(1);
        private int _value;

        public async Task IncrementValue()
        {
            await _mutex.WaitAsync();
            try
            {
                var oldValue = _value;
                await Task.Delay(0);
                _value = oldValue + 1;
            }
            finally
            {
                _mutex.Release();
            }
        }

        public static void Main(string[] args)
        {

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
