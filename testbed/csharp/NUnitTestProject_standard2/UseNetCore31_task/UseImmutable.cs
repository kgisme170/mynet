using System;
using System.Collections.Concurrent;
using System.Collections.Immutable;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class UseImmutable
    {
        public static async Task Test()
        {
            var stack = ImmutableStack<int>.Empty;
            stack = stack.Push(33);
            stack = stack.Push(23);

            foreach (var i in stack)
            {
                Console.WriteLine(i);
            }

            stack = stack.Pop(out _);
            Console.WriteLine(stack);

            var list = ImmutableList<int>.Empty;
            list = list.Insert(0, 1);
            list = list.Insert(0, 2);
            list = list.RemoveAt(1);

            var hashSet = ImmutableHashSet<int>.Empty;
            hashSet = hashSet.Add(3);

            var dict = ImmutableDictionary<int, string>.Empty;
            dict = dict.Add(10, "Ten");
            dict = dict.SetItem(10, "Shi");

            var dictionary = new ConcurrentDictionary<int, string>();
            var newValue = dictionary.AddOrUpdate(0,
                k => "zero",
                (k, oldValue) => "zero");
            Console.WriteLine(newValue);

            var blockingQueue = new BlockingCollection<int>(boundedCapacity: 1);//todo 
            blockingQueue.Add(7);
            blockingQueue.Add(8);
            blockingQueue.CompleteAdding();

            foreach (var i in blockingQueue.GetConsumingEnumerable())
            {
                Console.WriteLine(i);
            }

            var asyncQueue = new BufferBlock<int>();
            await asyncQueue.SendAsync(7);
            await asyncQueue.SendAsync(8);
            asyncQueue.Complete();

            while (await asyncQueue.OutputAvailableAsync())
            {
                Console.WriteLine(await asyncQueue.ReceiveAsync());
            }
        }
    }
}
