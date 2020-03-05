using Nito.AsyncEx;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reactive.Linq;
using System.Threading.Tasks;

namespace UseNetStandard2_task
{
    class Program
    {

        public static async Task MainAsync(string[] args)
        {
            await Task.Delay(TimeSpan.FromSeconds(2));
        }

        public static void Main(string[] args)
        {
            Console.WriteLine("hi");
            /*
            try
            {
                AsyncContext.Run(() => MainAsync(args));
            }
            catch (Exception e)
            {
                Console.Error.WriteLine(e);
            }*/

            IObservable<DateTimeOffset> timestamps =
                Observable.Interval(TimeSpan.FromSeconds(1))
                .Timestamp()
                .Where(x => x.Value % 2 == 0)
                .Select(x => x.Timestamp);
            timestamps.Subscribe(x => Console.WriteLine(x));
        }
    }
}
