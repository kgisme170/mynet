using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Reactive;
using System.Reactive.Disposables;
using System.Reactive.Linq;
using System.Reactive.Subjects;
using System.Threading;

namespace LinqRx
{
    class UseRx
    {
        static IObservable<int> GetObServable1()
        {
            return Observable.Create<int>(observer =>
            {
                Console.WriteLine("sth");
                observer.OnNext(1);
                observer.OnNext(2);
                observer.OnNext(3);
                observer.OnNext(4);
                observer.OnCompleted();
                return Disposable.Empty;
            });
        }

        static IObservable<int> GetObServable2()
        {
            return Observable.Create<int>(observer =>
            {
                Timer timer = null;
                int counter = 0;
                timer = new Timer(o =>
                {
                    if (counter == 3)
                    {
                        observer.OnCompleted();
                    }

                    observer.OnNext(counter);
                    Interlocked.Increment(ref counter);
                }, null, TimeSpan.Zero, TimeSpan.FromMilliseconds(500));
                return Disposable.Create(() => Console.WriteLine("Disposable called"));
            });
        }

        static IObservable<long> GetObServable3()
        {
            return Observable.Create<long>(observer =>
            {
                var timerObservable = Observable.Timer(
                    TimeSpan.FromMilliseconds(0),
                    TimeSpan.FromMilliseconds(500))
                    .Skip(1) // to skip value=0
                    .Take(3);

                return timerObservable.Subscribe(observer);
            });
        }

        static IObservable<string> GetObServable4()
        {
            var period = TimeSpan.FromMilliseconds(500);
            var enumerable = new List<string> { "one", "two", "three" };
            //var observable = default(IObservable<long>);
            return Observable.Interval(period)
                .Zip(enumerable.ToObservable(), (n, s) => s);
        }

        static IObservable<int> GetObservable5()
        {
            return Observable.Create<int>(observer =>
            {
                observer.OnNext(10);
                return Disposable.Create(() =>
                {
                    var stack = new StackTrace();
                    var methodBase = stack.GetFrame(1)?.GetMethod();
                    var typename = methodBase?.DeclaringType.FullName;
                    var methodName = methodBase.Name;
                    var msg = string.Format($"Dispose called by {typename}.{methodName}.");
                    Debug.Print(msg);
                    Console.WriteLine(msg);
                });
            });
        }
        public static void Test()
        {
            var observable = GetObservable5();
            var subscription = observable.Subscribe(
                x => Console.WriteLine($"value={x}"),
                e => Console.WriteLine(e.Message),
                () => Console.WriteLine("Completed")
            );
            Console.WriteLine("Press any key to dispose subscription");
            Console.ReadKey();
            subscription.Dispose();
        }
        public static void Test3()
        {
            var subject = new Subject<string>();
            var o1 = new[] { "hi", "there" }.ToObservable();
            var s1 = subject.Subscribe(v => Console.WriteLine("Sub1:" + v.ToString()));
            var s2 = subject.Subscribe(v => { Thread.Sleep(500); Console.WriteLine("Sub2:" + v.ToString()); });
            var s3 = subject.Subscribe(v => Debug.Print("Sub3:" + v.ToString()));
            var s4 = o1.Subscribe(subject);
            s1.Dispose();
            s2.Dispose();
            s3.Dispose();
            s4.Dispose();
        }
        public static void Test2()
        {
            var observable = new MyRangeObservable(5, 8);
            var observer = Observer.Create<int>(
                x => Console.WriteLine($"value={x}"),
                e => Console.WriteLine(e.Message),
                () => Console.WriteLine("Completed")
            );
            var sub = observable.Subscribe(observer);
            sub.Dispose();

            var subject = new Subject<int>();
            var sub2 = subject.Subscribe(Console.WriteLine);
            subject.OnNext(1);
            subject.OnNext(11);
            subject.OnNext(12);
            sub2.Dispose();
            subject.OnNext(33);
        }
        public static void Test1()
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}");

            IObservable<int> input = Observable.Range(1, 15);
            input.Where(i => i % 2 == 0).Subscribe(Console.Write);

            var ob = new MyObserver<int>();
            var subscripttion = input.Subscribe(ob);

            var observable = new MyRangeObservable(5, 8);
            var subscription2 = observable.Subscribe(ob);

            Console.ReadKey();
            subscripttion.Dispose();
            subscription2.Dispose();
        }
    }

    class Observer1 : IObserver<int> // 消费
    {
        public void OnCompleted()
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}");
            Console.WriteLine("Observation complete");
        }

        public void OnError(Exception error)
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}");
            Console.WriteLine($"Error: {error.Message}");
        }

        public void OnNext(int value)
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}, value = {value}");
        }
    }

    class MyObserver<T> : IObserver<T> // 消费
    {
        public void OnCompleted()
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}");
            Console.WriteLine("Observation complete");
        }

        public void OnError(Exception error)
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}");
            Console.WriteLine($"Error: {error.Message}");
        }

        public void OnNext(T value)
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}, value = {value}");
        }
    }

    class MyRangeObservable : IObservable<int> // 生产者
    {
        private int _start;
        private int _count;
        public MyRangeObservable(int start, int count)
        {
            _start = start;
            _count = count;
        }
        public IDisposable Subscribe(IObserver<int> observer)
        {
            try
            {
                //Task.Run(() =>
                //{
                    for (int i = _start; i < _start + _count; ++i)
                    {
                        observer.OnNext(i);
                    }
                    observer.OnCompleted();
                //});
            }
            catch (Exception e)
            {
                observer.OnError(e);
            }
            return new MyDisposable();
        }
    }

    internal class MyDisposable : IDisposable
    {
        public void Dispose()
        {
            Console.WriteLine("Dispose");
        }
    }
}
