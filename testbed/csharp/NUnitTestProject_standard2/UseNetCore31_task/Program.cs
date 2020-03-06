using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Reactive.Disposables;
using System.Reactive.Linq;
using System.Runtime.InteropServices.ComTypes;
using System.Threading;
using System.Threading.Tasks;

namespace TestRx
{
    interface IMy
    {
        
    }
    class Program
    {
        test !
        private static async Task ProcessAsync(string s)
        {
            Console.WriteLine("call function");
            if (s == null)
            {
                Console.WriteLine("throw");
                throw new ArgumentNullException("s");
            }
            Console.WriteLine("print");
            await Task.Run(() => Console.WriteLine(s));
            Console.WriteLine("end");
        }

        private static async void Caller()
        {
            try
            {
                await ProcessAsync("");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        public static void Main(string[] args)
        {
            Caller();
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
