using System;
using System.ComponentModel;
using System.Linq;
using System.Reactive.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace TestRx
{
    class Program
    {
        public static async Task<int> F01(int i)
        {
            await Task.Delay(TimeSpan.FromSeconds(3));
            if (i == 1)
            {
                throw new Exception("ok");
            }
            await Task.Delay(TimeSpan.FromSeconds(3));
            return 2;
        }

        public static void UseBgWorker()
        {
            var bgworker = new BackgroundWorker()
            {
                WorkerSupportsCancellation = true
            };
            bgworker.DoWork += (object sender, DoWorkEventArgs e) =>
            {
                Console.WriteLine("Begin bgworker sleep {0}", DateTime.Now);
                Thread.Sleep(15000);
                Console.WriteLine("bgworker ends {0}", DateTime.Now);
            };
            bgworker.RunWorkerAsync();
            Console.WriteLine(bgworker.CancellationPending);
            Thread.Sleep(3000);
            Console.WriteLine("Call cancel {0}", DateTime.Now);
            Console.WriteLine(bgworker.CancellationPending);
            Console.WriteLine("-----------");
            bgworker.CancelAsync();
            Console.WriteLine(bgworker.CancellationPending);
            Console.WriteLine(bgworker.CancellationPending);
            Thread.Sleep(3000);
            Console.WriteLine(bgworker.CancellationPending);
            Console.WriteLine("main ends {0}", DateTime.Now);
        }

        public class CustomException : Exception
        {
            public CustomException(string message) : base(message)
            { }
        }

        public static void F2()
        {
            var task1 = Task.Factory.StartNew(() => {
                var child1 = Task.Factory.StartNew(() => {
                    var child2 = Task.Factory.StartNew(() => {
                        // This exception is nested inside three AggregateExceptions.
                        throw new CustomException("Attached child2 faulted.");
                    }, TaskCreationOptions.AttachedToParent);

                    // This exception is nested inside two AggregateExceptions.
                    throw new CustomException("Attached child1 faulted.");
                }, TaskCreationOptions.AttachedToParent);
            });

            try
            {
                task1.Wait();
            }
            catch (AggregateException ae)
            {
                foreach (var e in ae.Flatten().InnerExceptions)
                {
                    if (e is CustomException)
                    {
                        Console.WriteLine(e.Message);
                    }
                    else
                    {
                        throw;
                    }
                }
            }
        }

        public static void F3()
        {
            Console.WriteLine("hi");

            try
            {
                var task = F01(1);
                task.Wait();
            }
            /*
            catch (AggregateException e)
            {
                Console.WriteLine(e.Message);
            }*/
            catch (Exception e)
            {
                Console.WriteLine(e.GetType().Name);
                Console.WriteLine(e.Message);
            }
        }

        public static async Task<int> F4()
        {
            await Task.Delay(1000);
            return await Task<int>.Run(() =>
            {
                Console.WriteLine("");
                return 2;
            }); // CompletedTask;
        }

        public static async Task F5()
        {
            await Task.Delay(1000);
        }

        public static async Task<Task> F6()
        {
            return Task.CompletedTask;
        }

        public static void Main(string[] args)
        {
            // F2();
            // var t = await F4();
            /*
            IObservable<DateTimeOffset> timestamps =
                Observable.Interval(TimeSpan.FromSeconds(1))
                .Timestamp()
                .Where(x => x.Value % 2 == 0)
                .Select(x => x.Timestamp);
            timestamps.Subscribe(x => Console.WriteLine(x));
            */
        }
    }
}
