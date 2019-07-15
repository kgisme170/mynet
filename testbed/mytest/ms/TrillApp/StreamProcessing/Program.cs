using Microsoft.StreamProcessing;
using System;
using System.Linq;
using System.Reactive.Linq;

namespace StreamProcessing
{
    class Program
    {
        static void Main(string[] args)
        {
            /*
            var values = new[]
            {
                new {Field1=1,Field2=3},
                new {Field1=1,Field2=3},
                new {Field1=1,Field2=3},
                new {Field1=1,Field2=3},
                new {Field1=1,Field2=3},
            };
            */

            StreamEvent<int>[] values =
            {
                StreamEvent.CreateInterval(1, 10, 1),
                StreamEvent.CreateInterval(2, 10, 2),
                StreamEvent.CreateInterval(3, 10, 3),
                StreamEvent.CreateInterval(4, 10, 4),
                StreamEvent.CreateInterval(5, 10, 5),
                StreamEvent.CreateInterval(6, 10, 6),
                StreamEvent.CreateInterval(7, 10, 7),
                StreamEvent.CreateInterval(8, 10, 8),
                StreamEvent.CreateInterval(9, 10, 9),
                StreamEvent.CreatePunctuation<int>(StreamEvent.InfinitySyncTime)
            };

            var input = values.ToObservable().ToStreamable();
            Console.WriteLine("Input =");
            input.ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();

            Console.WriteLine();
            var output = input.Where(r => r > 5);

            Console.WriteLine();
            Console.WriteLine("Output =");
            output.ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();
        }
    }
}
