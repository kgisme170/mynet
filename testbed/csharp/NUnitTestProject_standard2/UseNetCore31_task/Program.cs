using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Reactive.Disposables;
using System.Reactive.Linq;
using System.Runtime.InteropServices.ComTypes;
using System.Threading;
using System.Threading.Tasks;

namespace TestRx
{
    class Program
    {
        static void Main(string[] args)
        {
            Observable.Range(1, 5).Subscribe(async x => await DoTheThing(x));
            Console.WriteLine("done");
        }

        static async Task DoTheThing(int x)
        {
            await Task.Delay(TimeSpan.FromSeconds(x));
            Console.WriteLine(x);
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
