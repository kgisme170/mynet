using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class UseBounded
    {
        IPropagatorBlock<int, int> DataFlowTest()
        {
            var opt = new ExecutionDataflowBlockOptions
            {
                MaxDegreeOfParallelism = 8,
            };
            return new TransformBlock<int, int>(data => data * 2, opt);
        }

        IEnumerable<int> PLinqTest(IEnumerable<int> input)
        {
            return input.AsParallel()
                .WithDegreeOfParallelism(8)
                .Select(x => x * 2);
        }

        void UseParallelClass(IEnumerable<int> input)
        {
            var opt = new ParallelOptions
            {
                MaxDegreeOfParallelism = 8,
            };
            Parallel.ForEach(input, opt, x => Console.WriteLine(x));
        }

        async Task<int[]> UseSemaphore(IEnumerable<int> input)
        {
            var semaphore = new SemaphoreSlim(8);
            var tasks = input.Select(async i =>
            {
                await semaphore.WaitAsync();
                try
                {
                    Console.WriteLine(i);
                    return i;
                }
                finally
                {
                    semaphore.Release();
                }
            }).ToArray();
            return await Task.WhenAll(tasks);
        }
    }
}
