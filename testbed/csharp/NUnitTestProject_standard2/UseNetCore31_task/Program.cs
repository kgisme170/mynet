﻿using System;
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
    class Program
    {
        public static async Task Throw01()
        {
            await Task.Delay(0);
            throw new Exception("ok");
        }

        public static async Task Catch()
        {
            try
            {
                await Throw01();
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        public static async Task Main(string[] args)
        {
            await Catch();
            Environment.Exit(0);

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
