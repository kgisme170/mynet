using Moq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Reactive.Disposables;
using System.Reactive.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class Program
    {
        public static void Main(string[] args)
        {
            Observable.Interval(TimeSpan.FromSeconds(1))
                .Window(2)
                .Subscribe(group =>
                {
                    Trace.WriteLine(DateTime.Now.Second + ": Starting new group");
                    group.Subscribe(
                        x => Console.WriteLine(DateTime.Now.Second + ": Saw " + x),
                        () => Console.WriteLine(DateTime.Now.Second + ": Ending group"));
                });
            Console.ReadKey();
        }
        
        public static void Main2(string[] args)
        {
            IObservable<DateTimeOffset> timestamps =
                Observable.Interval(TimeSpan.FromSeconds(1))
                .Timestamp()
                .Where(x => x.Value % 2 == 0)
                .Select(x => x.Timestamp);
            timestamps.Subscribe(x => Console.WriteLine(x));

            var timer = Observable.Timer(dueTime: TimeSpan.FromSeconds(2),
                period: TimeSpan.FromSeconds(1));
            timer.Subscribe(x => Console.WriteLine(x));
            Console.ReadLine();
        }
    }
}
