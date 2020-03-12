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
