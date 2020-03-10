using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Reactive.Disposables;
using System.Reactive.Linq;
using System.Threading;
using System.Threading.Tasks;

using Microsoft.Extensions.Logging;

namespace TestRx
{
    public interface Imy
    {
        int X { get; set; }
    }

    public class MyImpl : Imy
    {
        private int _x;
        int Imy.X
        {
            get => _x;
            set => _x = value;
        }
    }

    class Program
    {
        public void ExpectException()
        {
            var task1 = Task.Run(() => { throw new CustomException("This exception is expected!"); });

            while (!task1.IsCompleted) { }
            if (task1.Status == TaskStatus.Faulted)
            {
                if (task1.Exception == null)
                {
                    return;
                }

                var inner = task1.Exception!.InnerExceptions;
                foreach (var e in inner)
                {
                    if (e is CustomException)
                    {
                        Console.WriteLine(e.Message);
                    }
                    else
                    {
                        throw e;
                    }
                }
            }
        }

        static void CurrentDomain_UnhandledException(object sender, UnhandledExceptionEventArgs e)
        {
            var eo = e.ExceptionObject;
            var ex = eo as Exception ?? throw new Exception("---------------------e is null");
            Console.WriteLine("------------handle exception-----------");
            Console.WriteLine(ex.Message);
        }

        public class CustomException : Exception
        {
            public CustomException(string message) : base(message) { }
        }

        static void Main(string[] args)
        {
            var task = Task.Run(() => { throw new Exception("This exception is expected!"); });
            AppDomain.CurrentDomain.UnhandledException += (object sender, UnhandledExceptionEventArgs e) =>
            {
                var eo = e.ExceptionObject;
                var ex = eo as Exception ?? throw new Exception("---------------------e is null");
                Console.WriteLine("------------handle exception-----------");
                Console.WriteLine(ex.Message);
            };
            try
            {
                task.Wait();
            }
            catch (AggregateException ae)
            {
                // Call the Handle method to handle the custom exception,
                // otherwise rethrow the exception.
                ae.Handle(ex => {
                    if (ex is CustomException)
                        Console.WriteLine(ex.Message);
                    return ex is CustomException;
                });
            }
            Console.WriteLine("end main");
        }

        public void FlatternException()
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

        public void CreateLogger()
        {
            using var loggerFactory = LoggerFactory.Create(builder =>
            {
                builder.AddConsole();
            });
            ILogger logger = loggerFactory.CreateLogger<Program>();
            logger.LogInformation("Example log message");

            var o = new MyImpl();
            ((Imy)o).X = 3;

            Observable.Range(1, 5).Subscribe(async x => await DoTheThing(x));
            Console.WriteLine("done");
        }

        static async Task DoTheThing(int x)
        {
            await Task.Delay(TimeSpan.FromSeconds(x));
            Console.WriteLine(x);
        }

        public static void Main2(string[] args)
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
