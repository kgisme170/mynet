using System;
using System.Reactive.Disposables;
using System.Reactive.Linq;
using System.Threading;

namespace UseNetCore31_task
{
    public static class ObservableProgress
    {
        public static IObservable<T> Create<T>(this Action<IProgress<T>> action)
        {
            return Observable.Create<T>(obs =>
            {
                action(new DelegateProgress<T>(obs.OnNext));
                obs.OnCompleted();
                //No apparent cancellation support.
                return Disposable.Empty;
            });
        }
    }

    class DelegateProgress<T> : IProgress<T>
    {
        private readonly Action<T> _report;
        public DelegateProgress(Action<T> report)
        {
            if (report == null) throw new ArgumentNullException();
            _report = report;
        }
        public void Report(T value)
        {
            _report(value);
        }
    }

    partial class Program
    {
        private static void Solve(IProgress<int> progress)
        {
            for (int i = 0; i < 100; i++)
            {
                Thread.Sleep(10);
                progress.Report(i);
            }
        }

        private static void UseIProgress()
        {
            Solve(new Progress<int>(    // ctor parameter of Progress<> it
                i => Console.Write(".") // Report function
                ));
            Console.WriteLine("Done");

            Observable.Create<int>(obs =>
            {
                Solve(new Progress<int>(obs.OnNext));
                obs.OnCompleted();
                return Disposable.Empty;
            })
            .Subscribe(
                i => Console.Write("#"), // obs.OnNext() function
                () => Console.WriteLine("Done")); // obs.OnCompleted()

            ObservableProgress.Create<int>(Solve)
            .Subscribe(
                i => Console.Write("."),
                () => Console.WriteLine("Done"));
        }
    }
}
