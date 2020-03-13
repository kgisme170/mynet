using Nito.AsyncEx;
using System.Threading.Tasks;

namespace UseNetCore31_task
{
    class UseAsyncSignales
    {
        private readonly TaskCompletionSource<object> taskCompletionSource = new TaskCompletionSource<object>();
        private readonly AsyncManualResetEvent asyncManualResetEvent = new AsyncManualResetEvent();
        private int _value1;
        private int _value2;

        public async Task<int> WaitForInitializationAsync()
        {
            await taskCompletionSource.Task;
            return _value1 + _value2;
        }

        public void Init()
        {
            _value1 = 1;
            _value2 = 2;
            taskCompletionSource.TrySetResult(0);
        }

        public async Task WaitForConnectedAsync()
        {
            await asyncManualResetEvent.WaitAsync();
        }

        public void ConnectedChanged(bool connected)
        {
            if (connected)
            {
                asyncManualResetEvent.Set();
            }
            else
            {
                asyncManualResetEvent.Reset();
            }
        }
    }
}
