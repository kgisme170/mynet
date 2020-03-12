using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace UseNetCore31_task
{
    class UseParallel
    {
        static void TestParallel()
        {
            var list = new List<int> { 1, 3, 6, 7, 9, 8, 12, 5, 1, 3, 6, 7, 9, 8, 12, 5 };
            Parallel.ForEach(list, i => Console.WriteLine(i));

            Parallel.ForEach(list, (i, state) =>
            {
                if (i == 7)
                {
                    state.Stop();
                }
                else
                    Console.WriteLine(i);
            });

            var tokenSource = new CancellationTokenSource();
            Parallel.ForEach(list, new ParallelOptions { CancellationToken = tokenSource.Token }, i =>
            {
                Thread.Sleep(500);
                Console.WriteLine(i);
            });
            var ag = list
                .AsParallel()
                .WithCancellation(tokenSource.Token)
                .Aggregate(seed: 0, func: (sum, item) => sum + item);
            Console.WriteLine(ag);
        }
    }
}
