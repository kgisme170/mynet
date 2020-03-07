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

        public static void Main(string[] args)
        {
            /*
            Observable.Throw<ApplicationException>(
                new ApplicationException("something bad happened")
                ).Subscribe(Console.WriteLine);
            */

            /*
            IObservable<string?> lines =
                Observable.Using(
                    () => File.OpenText("m.txt"),
                    stream =>
                    Observable.Generate(
                        stream, //initial state
                        s => !s.EndOfStream,
                        s => s, //the stream is our state, it holds the position in the file 
                        s => s.ReadLine()) //each iteration will emit the current line (and moves to the next)
                );
            lines.Subscribe(Console.WriteLine);
            Environment.Exit(0);
            */

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
