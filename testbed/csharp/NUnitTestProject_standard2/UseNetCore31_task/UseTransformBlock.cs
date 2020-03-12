using System;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class UseTransformBlock
    {
        static void Consume1()
        {
            var multiplyBlock = new TransformBlock<int, int>(x => x * 2, new ExecutionDataflowBlockOptions { MaxDegreeOfParallelism = DataflowBlockOptions.Unbounded });
            var additionBlock = new TransformBlock<int, int>(x => x + 2);
            var outputBlock = new ActionBlock<int>(Console.WriteLine);

            multiplyBlock.LinkTo(additionBlock, new DataflowLinkOptions { PropagateCompletion = true });
            additionBlock.LinkTo(outputBlock, new DataflowLinkOptions { PropagateCompletion = true });
            multiplyBlock.Post(3);

            multiplyBlock.Complete();
            outputBlock.Completion.Wait();
        }

        static void Consume2()
        {
            var printResult = new ActionBlock<int>(x =>
            {
                Console.WriteLine(x);
            });
            var countBytes = new TransformBlock<int, int>(
                new Func<int, int>((x) => { return 2 * x; }));
            countBytes.LinkTo(printResult, new DataflowLinkOptions { PropagateCompletion = true });
            countBytes.Post(1);
            printResult.Completion.ContinueWith(x => Console.WriteLine("printResult done"));
            countBytes.Completion.ContinueWith(x => Console.WriteLine("countBytes done"));
            countBytes.Complete(); // start async task in the backend
            printResult.Completion.Wait();
            Console.WriteLine("Done");
            Console.ReadKey();
            Environment.Exit(1);
        }

        static async Task Consume3()
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
                using var link = block.LinkTo(outputBlock,
                    new DataflowLinkOptions
                    {
                        PropagateCompletion = true,
                    });// break link by link.Dispose()
                block.Post(4);
                block.Complete();
                await block.Completion;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        static IPropagatorBlock<int, int> CreateMyCustomBlock(CancellationToken token)
        {
            var blockOptions = new ExecutionDataflowBlockOptions
            {
                CancellationToken = token,
            };

            var multiplyBlock = new TransformBlock<int, int>(x => x * 2, blockOptions);
            var additionBlock = new TransformBlock<int, int>(x => x + 2, blockOptions);
            var divisionBlock = new TransformBlock<int, int>(x => x / 2, blockOptions);

            var flowCompletion = new DataflowLinkOptions
            {
                PropagateCompletion = true
            };
            multiplyBlock.LinkTo(additionBlock, flowCompletion);
            additionBlock.LinkTo(divisionBlock, flowCompletion);

            return DataflowBlock.Encapsulate(multiplyBlock, divisionBlock);
        }
    }
}
