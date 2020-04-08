using Nito.AsyncEx;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Reactive.Linq;
using System.Reactive.Threading.Tasks;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class Program
    {
        public static void DoProcessing()
        {
            TraceMessage("Something happened.");
        }

        public static void TraceMessage(string message,
                [System.Runtime.CompilerServices.CallerMemberName] string memberName = "",
                [System.Runtime.CompilerServices.CallerFilePath] string sourceFilePath = "",
                [System.Runtime.CompilerServices.CallerLineNumber] int sourceLineNumber = 0)
        {
            Console.WriteLine("message: " + message);
            Console.WriteLine("member name: " + memberName);
            Console.WriteLine("source file path: " + sourceFilePath);
            Console.WriteLine("source line number: " + sourceLineNumber);
        }

        public static void Main(string [] args)
        {
            var stackTrace = new StackTrace(0, true);
            var sf = stackTrace.GetFrame(0)!;
            Console.WriteLine("FileName: {0}", sf.GetFileName());
            Console.WriteLine("Line Number:{0} ", sf.GetFileLineNumber());
            Console.WriteLine("Function Name:{0}", sf.GetMethod());
            Console.WriteLine(nameof(Console));

            DoProcessing();
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
