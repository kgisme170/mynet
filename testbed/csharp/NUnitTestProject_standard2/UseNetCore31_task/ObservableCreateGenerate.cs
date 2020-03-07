using System;
using System.IO;
using System.Linq;
using System.Reactive.Disposables;
using System.Reactive.Linq;

namespace UseNetCore31_task
{
    partial class Program
    {
        private static void TestCreate()
        {
            Console.WriteLine("1..........");
            Observable.Create<int>(ob =>
            {
                for (int i = 0; i < 50; ++i)
                {
                    ob.OnNext(i); // terminate by function
                }
                ob.OnCompleted();
                return Disposable.Empty;
            }).Subscribe(i => Console.Write($"{i},"), () => Console.WriteLine("Done"));

            Console.WriteLine("\n2..........");
            Observable.Generate(
                0,              //initial state
                i => i < 10,    //condition (false means terminate)
                i => i + 1,     //next iteration step
                i => i * 2).Subscribe(Console.Write);
            Console.WriteLine("\n3..........");
            Observable.Range(0, 10).Select(i => i * 2).Subscribe(Console.Write);
            Console.WriteLine("\n4..........");
            Observable.Return("Hello World").Subscribe(Console.Write);
            Console.WriteLine("\n5..........");
            Observable.Never<string>().Subscribe(Console.Write);
            Observable.Empty<string>().Subscribe(Console.Write);
            Console.WriteLine("\n6..........");
            Enumerable.Range(1, 10).ToObservable().Subscribe(Console.Write);
            Console.WriteLine("\n7..........");

            Observable.Throw<ApplicationException>(
                new ApplicationException("something bad happened")
                ).Subscribe(Console.WriteLine);
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
        }
    }
}
