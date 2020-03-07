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
            var o = Observable.Create<int>(ob =>
            {
                for (int i = 0; i < 5; ++i)
                {
                    ob.OnNext(i);
                }
                ob.OnCompleted();
                return Disposable.Empty;
            });
            o.Subscribe(Console.WriteLine);

            IObservable<int> o1 =
                Observable.Generate(
                    0,              //initial state
                    i => i < 10,    //condition (false means terminate)
                    i => i + 1,     //next iteration step
                    i => i * 2);    //the value in each iteration

            IObservable<int> o2 = Observable.Range(0, 10).Select(i => i * 2);

            var o3 = Observable.Return("Hello World");//创建单个元素的可观察序列
            var o4 = Observable.Never<string>();//创建一个空的永远不会结束的可观察序列
            var o5 = Observable.Throw<ApplicationException>(new ApplicationException("something bad happened"));//创建一个抛出指定异常的可观察序列
            var o6 = Observable.Empty<string>();//创建一个空的立即结束的可观察序列

            var o7 = Enumerable.Range(1, 10).ToObservable();

            IObservable<string?> lines =
                Observable.Using(
                    () => File.OpenText("TextFile.txt"), // opens the file and returns the stream we work with
                    stream =>
                    Observable.Generate(
                        stream, //initial state
                        s => !s.EndOfStream, //we continue until we reach the end of the file
                        s => s, //the stream is our state, it holds the position in the file 
                        s => s.ReadLine()) //each iteration will emit the current line (and moves to the next)
                );
            /*
            IObservable<DateTimeOffset> timestamps =
                Observable.Interval(TimeSpan.FromSeconds(1))
                .Timestamp()
                .Where(x => x.Value % 2 == 0)
                .Select(x => x.Timestamp);
            timestamps.Subscribe(x => Console.WriteLine(x));
            */

            var timer = Observable.Timer(dueTime: TimeSpan.FromSeconds(2),
                period: TimeSpan.FromSeconds(1));
            timer.Subscribe(x => Console.WriteLine(x));
            Console.ReadLine();
        }
    }
}
