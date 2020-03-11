﻿using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class UseTransformBlock
    {
        static void ConsumeTransformBlock()
        {
            var multiplyBlock = new TransformBlock<int, int>(x => x * 2);
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
            countBytes.Complete();
            printResult.Completion.Wait();
            Console.WriteLine("Done");
            Console.ReadKey();
            Environment.Exit(1);
        }
    }
}
