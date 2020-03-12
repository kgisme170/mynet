using System;
using System.Reactive.Linq;
using System.Threading;

namespace UseNetCore31_task
{
    class ObservableInterval
    {
        public static void Interval1(string[] args)
        {
            Observable.Interval(TimeSpan.FromSeconds(1))
                .Window(2)
                .Subscribe(group =>
                {
                    Console.WriteLine(DateTime.Now.Second + ": Starting new group");
                    group.Subscribe(
                        x => Console.WriteLine(DateTime.Now.Second + ": Saw " + x),
                        () => Console.WriteLine(DateTime.Now.Second + ": Ending group"));
                });
            Console.ReadKey();
        }

        public static void Interval2(string[] args)
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
