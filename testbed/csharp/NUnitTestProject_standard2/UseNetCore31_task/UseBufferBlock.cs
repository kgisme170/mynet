using System;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class UseBufferBlock
    {
        public static void Test()
        {
            var bufferBlock = new BufferBlock<int>();

            // Post more messages to the block.
            for (int i = 0; i < 3; i++)
            {
                bufferBlock.Post(i);
            }

            // Receive the messages back from the block.
            int value;
            while (bufferBlock.TryReceive(out value))
            {
                Console.WriteLine(value);
            }

            BufferBlock<int> bb = new BufferBlock<int>();

            ActionBlock<int> a1 = new ActionBlock<int>(a =>
            {
                Thread.Sleep(100);
                Console.WriteLine("Action A1 executing with value {0}", a);
            });

            ActionBlock<int> a2 = new ActionBlock<int>(a =>
            {
                Thread.Sleep(50);
                Console.WriteLine("Action A2 executing with value {0}", a);
            });

            ActionBlock<int> a3 = new ActionBlock<int>(a =>
            {
                Thread.Sleep(50);
                Console.WriteLine("Action A3 executing with value {0}", a);
            });

            bb.LinkTo(a1);
            bb.LinkTo(a2);
            bb.LinkTo(a3);

            Task t = new Task(() =>
            {
                int i = 0;
                while (i < 10)
                {
                    Thread.Sleep(50);
                    i++;
                    bb.Post(i);
                }
            }
            );

            t.Start();
        }
    }
}
