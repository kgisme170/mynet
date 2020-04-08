using Nito.AsyncEx;
using System;
using System.Threading;
using System.Threading.Tasks;

namespace UseNetCore31_task
{
    class AsyncProperty
    {
        public AsyncLazy<int> Data { get; } = new AsyncLazy<int>(async () =>
        {
            await Task.Delay(3000);
            return 33;
        });

        private readonly SemaphoreSlim _mutex = new SemaphoreSlim(1);
        private int _value = 3;

        public async Task IncrementValue()
        {
            await _mutex.WaitAsync();
            try
            {
                var oldValue = _value;
                await Task.Delay(0);
                _value = oldValue + 1;
            }
            finally
            {
                _mutex.Release();
            }
        }

        public void Print()
        {
            Console.WriteLine(_value);
        }
    }
}
