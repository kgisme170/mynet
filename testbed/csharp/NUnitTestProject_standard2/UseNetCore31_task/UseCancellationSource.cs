using System;
using System.Threading;
using System.Threading.Tasks;

namespace UseNetCore31_task
{
    class UseCancellationSource
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

        public static async Task Test1()
        {
            var cts = new CancellationTokenSource();
            var token = cts.Token;
            new Thread(() =>
            {
                Console.WriteLine("Press any key to end F");
                Console.ReadKey();
                cts.Cancel();
            }).Start();
            await F(token);
        }

        public static void Test2()
        {
            var cts = new CancellationTokenSource(TimeSpan.FromSeconds(5));
            var token = cts.Token;
            Console.WriteLine(token.IsCancellationRequested);
            cts.Cancel();
            Console.WriteLine(token.IsCancellationRequested);

            try
            {
                Console.WriteLine(token.IsCancellationRequested);
                var task = Task.Delay(3000, token);
                Console.WriteLine(token.IsCancellationRequested);
            }
            catch (OperationCanceledException)
            {
                Console.WriteLine("first");
            }
            catch (Exception)
            {
                Console.WriteLine("next");
            }

        }
    }
}
