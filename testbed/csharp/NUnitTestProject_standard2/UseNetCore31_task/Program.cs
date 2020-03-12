﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Reactive.Disposables;
using System.Reactive.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;
using Microsoft.Extensions.Logging;

namespace TestRx
{
    class Program
    {
        public static async Task Main(string[] args)
        {
            try
            {
                var block = new TransformBlock<int, int>(x =>
                {
                    if (x == 1)
                    {
                        throw new Exception("transform");
                    }
                    return x * 2;
                });

                var outputBlock = new ActionBlock<int>(Console.WriteLine);
                block.LinkTo(outputBlock, new DataflowLinkOptions { PropagateCompletion = true });
                block.Post(4);
                block.Complete();
                await block.Completion;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        static void UseTransformBlock()
        {
            var list = new List<int> { 1, 3, 6, 7, 9, 8, 12, 5, 1, 3, 6, 7, 9, 8, 12, 5 };
            //Parallel.ForEach(list, i => Console.WriteLine(i));
            /*
            Parallel.ForEach(list, (i, state) =>
            {
                if (i == 7)
                {
                    state.Stop();
                }
                else
                    Console.WriteLine(i);
            });
            */
            var tokenSource = new CancellationTokenSource();
            Parallel.ForEach(list, new ParallelOptions { CancellationToken = tokenSource.Token }, i =>
            {
                Thread.Sleep(500);
                Console.WriteLine(i);
            });
            var ag = list.AsParallel().Aggregate(seed: 0, func: (sum, item) => sum + item);
            Console.WriteLine(ag);
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
