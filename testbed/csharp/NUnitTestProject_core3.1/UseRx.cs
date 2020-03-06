using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Reactive;
using System.Reactive.Disposables;
using System.Reactive.Linq;
using System.Reactive.Subjects;
using System.Threading;

namespace NUnitTestProject_core
{
    class UseRx
    {
        //[Test]
        public static void BasicTest()
        {
            //使用Range方法返回Observable集合
            IObservable<Int32> input = Observable.Range(1, 15);
            input.Where(i => i % 2 == 0).Subscribe(x => Console.Write("{0} ", x));
            Console.WriteLine();

            //使用Array返回Observabale集合
            var myArray = new[] { 1, 3, 5, 7, 9 };
            IObservable<Int32> varmyObservable = myArray.ToObservable();
            varmyObservable.Subscribe(x => Console.WriteLine("Integer:{0}", x));
            Console.WriteLine();
            //Take操作符，用来指定获取集合中的前几项
            var take = new[] { 1, 2, 3, 4, 5, 4, 3, 2, 1 }.ToObservable();
            take.Take(5).Select(x => x * 10).Subscribe(x => Console.WriteLine(x));
            Console.WriteLine();
            //Skip操作符表示跳过集合中的n条记录。
            var skip = new[] { 1, 2, 3, 4, 5, 4, 3, 2, 1 }.ToObservable();
            skip.Skip(6).Select(x => x * 10).Subscribe(x => Console.WriteLine(x));
            Console.WriteLine();
            //Distinct操作符用来去除集合中的非重复数据。
            var distinct = new[] { 1, 2, 3, 4, 5, 4, 3, 2, 1 }.ToObservable();
            distinct.Distinct().Select(x => x * 10).Subscribe(x => Console.WriteLine(x));
            //Rx也需要释放资源
            Console.WriteLine();
            var ObservableStrings = Observable.Using<char, StreamReader>(
                () => new StreamReader(new FileStream("randomtext.txt", FileMode.Open)),
                streamReader => (streamReader.ReadToEnd().Select(str => str)).ToObservable()
                );
            ObservableStrings.Subscribe(Console.Write);

            //在Rx中Zip是将两个Observable对象合并为一个新的Observable对象。
            var numberCitys = varmyObservable.Zip(input, (range, array) => range + ":" + array);
            numberCitys.Subscribe(Console.WriteLine);
            Console.ReadKey();
        }

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
                int counter = 0;
                var timer = new Timer(o =>
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
                    var typename = methodBase!.DeclaringType!.FullName;
                    var methodName = methodBase.Name;
                    var msg = string.Format($"Dispose called by {typename}.{methodName}.");
                    Debug.Print(msg);
                    Console.WriteLine(msg);
                });
            });
        }
        [Test]
        public static void Test1()
        {
            var observable = GetObservable5();
            var subscription = observable.Subscribe(
                x => Console.WriteLine($"value={x}"),
                e => Console.WriteLine(e.Message),
                () => Console.WriteLine("Completed")
            );
            Console.WriteLine("Press any key to dispose subscription");
            subscription.Dispose();
        }
        [Test]

        public static void Test2()
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
        [Test]

        public static void Test3()
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
                    for (int i = _start; i < _start + _count; ++i)
                    {
                        observer.OnNext(i);
                    }
                    observer.OnCompleted();
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

        [Test]
        public static void Test4()
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}");

            IObservable<int> input = Observable.Range(1, 15);
            input.Where(i => i % 2 == 0).Subscribe(Console.Write);

            var ob = new MyObserver<int>();
            var subscripttion = input.Subscribe(ob);

            var observable = new MyRangeObservable(5, 8);
            var subscription2 = observable.Subscribe(ob);

            subscripttion.Dispose();
            subscription2.Dispose();
        }
    }
}
