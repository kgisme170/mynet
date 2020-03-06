    using System;
    using System.Linq;
    using System.Reactive.Linq;

    namespace TestRx
    {
        class Program
        {
            public static void Main(string[] args)
            {
                Console.WriteLine("hi");
                IObservable<DateTimeOffset> timestamps =
                    Observable.Interval(TimeSpan.FromSeconds(1))
                    .Timestamp()
                    .Where(x => x.Value % 2 == 0)
                    .Select(x => x.Timestamp);
                timestamps.Subscribe(x => Console.WriteLine(x));
            }
        }
    }
