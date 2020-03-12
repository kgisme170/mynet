using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Reactive.Linq;
using System.Reactive.Threading.Tasks;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;

namespace UseNetCore31_task
{
    class ToObservable
    {
        static async Task F(CancellationToken token = default)
        {
            try
            {
                await Task.Delay(10000, token);
                Console.WriteLine("End F");
            }
            catch (TaskCanceledException)
            {
                Console.WriteLine("Successfully cancelled f");
            }
        }

        static async Task<int> T()
        {
            await Task.Delay(0);
            return 3;
        }

        public static void UseToObservable()
        {
            var ob = T().ToObservable();
            var ob2 = Observable.FromAsync(T);

            var buffer = new BufferBlock<int>();
            var integers = buffer.AsObservable();
            integers.Subscribe(data => Console.WriteLine(data),
                ex => Console.WriteLine(ex),
                () => Console.WriteLine("Done"));
            buffer.Post(12);

            var client = new HttpClient();
            var response = client.GetAsync("http://www.baidu.com").ToObservable();
            var response2 = Observable.StartAsync(token => client.GetAsync("http://www.baidu.com", token));

            IObservable<string> urls = new List<string>() { "http://www.baidu.coM" }.ToObservable();
            var response3 = urls.SelectMany((url, token) => client.GetAsync(url, token));

            var ticks = Observable.Interval(TimeSpan.FromSeconds(1))
                .Timestamp()
                .Select(x => x.Timestamp)
                .Take(5);

            var display = new ActionBlock<DateTimeOffset>(x => Console.WriteLine(x));
            ticks.Subscribe(display.AsObserver());

            try
            {
                display.Completion.Wait();
                Console.WriteLine("Done");
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
        }
    }
}