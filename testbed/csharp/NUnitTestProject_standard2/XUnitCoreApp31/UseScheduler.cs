using Microsoft.Reactive.Testing;
using System;
using System.Collections.Generic;
using System.Reactive.Concurrency;
using System.Reactive.Linq;
using System.Text;
using Xunit;

namespace XUnitCoreApp31
{
    public class UseScheduler
    {
        public interface IHttpService
        {
            IObservable<string> GetString(string url);
        }

        public class MyTimeoutClass
        {
            private readonly IHttpService _httpService;
            public MyTimeoutClass(IHttpService httpService)
            {
                _httpService = httpService;
            }

            public IObservable<string> GetStringWithTimeout(string url, IScheduler scheduler)
            {
                return _httpService.GetString(url).Timeout(TimeSpan.FromSeconds(1), scheduler ?? Scheduler.Default);
            }
        }

        private class SuccessHttpServiceSub : IHttpService
        {
            public IScheduler Scheduler { get; set; }
            public TimeSpan Delay { get; set; }

            public IObservable<string> GetString(string url)
            {
                return Observable.Return("stub").Delay(Delay, Scheduler);
            }

            public SuccessHttpServiceSub(IScheduler scheduler, TimeSpan delay)
            {
                Scheduler = scheduler;
                Delay = delay;
            }
        }

        [Fact]
        public static void RunOk()
        {
            var scheduler = new TestScheduler();
            var stub = new SuccessHttpServiceSub(scheduler, TimeSpan.FromSeconds(0.5));
            var my = new MyTimeoutClass(stub);
            string result;
            my.GetStringWithTimeout("http://www.baidu.com", scheduler).Subscribe(r => { result = r; });
            scheduler.Start();
        }

        [Fact]
        public static void ThrowTimeoutException()
        {
            var scheduler = new TestScheduler();
            var stub = new SuccessHttpServiceSub(scheduler, TimeSpan.FromSeconds(1.5));
            var my = new MyTimeoutClass(stub);
            Exception result;
            my.GetStringWithTimeout("http://www.baidu.com", scheduler)
                .Subscribe(
                _ => Assert.True(false, "Received value"),
                ex => { result = ex; });
            scheduler.Start();
        }
    }
}
