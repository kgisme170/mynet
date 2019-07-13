﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Reactive.Linq;
using System.Reactive.Subjects;
using System.Reactive.Concurrency;
using System.Reactive.Threading;
using System.Threading;
namespace LinqRx
{
    class UseRx
    {
        public static void Test()
        {
            var id = Thread.CurrentThread.ManagedThreadId.ToString();
            Console.WriteLine($"Thread id = {id}");

            IObservable<int> input = Observable.Range(1, 15);
            input.Where(i => i % 2 == 0).Subscribe(Console.Write);

            var ob = new Observer();
            var subscripttion = input.Subscribe(ob);
            subscripttion.Dispose();

            var observable = new MyRangeObservable(5, 8);
            var subscription2 = observable.Subscribe(ob);
            subscription2.Dispose();
        }
    }

    class Observer : IObserver<int> // 消费
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

    class MyRangeObservable : IObservable<int>
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
}
