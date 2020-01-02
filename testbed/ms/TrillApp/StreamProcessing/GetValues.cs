using Microsoft.StreamProcessing;
using System;
using System.Linq;
using System.Reactive.Linq;

namespace StreamProcessing
{
    class GetValues
    {
        public static StreamEvent<int>[] Get()
        {
            return new[]
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
        }

        public static void Test1()
        {
            var values = Get();
            values.ToObservable().ToStreamable()
                .Where(r => r > 5)
                .ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();
            Console.WriteLine();

            values.ToObservable().ToStreamable()
                .Select(r => new { Original = r, Squared = r * r, Text = "Hello #" + r })
                .ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();
            Console.WriteLine();

            values.ToObservable().ToStreamable()
                .AlterEventLifetime(oldStart => oldStart + 5, 2)
                .ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();
            Console.WriteLine();

            values.ToObservable().ToStreamable()
                .SelectMany(r => Enumerable.Repeat(r, 5))
                .ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();
            Console.WriteLine();

            values.ToObservable().ToStreamable()
                .Count()
                .ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();
            Console.WriteLine();
        }

        public struct MyStruct
        {
            public int field1;
            public double field2;

            public override string ToString()
            {
                return "" + field1 + "\t" + field2;
            }
        }

        public static void Test()
        {
            var input =
                Observable
                    .Range(0, 30)
                    .Select(e => new MyStruct { field1 = e % 10, field2 = e + 0.5 })
                    .Select(e => StreamEvent.CreateStart(StreamEvent.MinSyncTime, e))
                    .ToStreamable()
                    .Cache();

            var query =
                input.SetProperty().IsConstantDuration(true)
                     .GroupBy(e => e.field2)
                     .SelectMany(str => str.Distinct(f => f.field1), (g, c) => c);

            query
                .ToStreamEventObservable()
                .Where(e => e.IsData)
                .ForEachAsync(e => Console.WriteLine("{0}\t{1}\t{2}", e.StartTime, e.EndTime, e.Payload)).Wait();  
        }
    }
}
