using System;
using System.Threading.Tasks;

namespace UseNetStandard2_task
{
    class Program
    {

        public static async Task Main(string[] args)
        {
            await Task.Delay(TimeSpan.FromSeconds(2));
        }
    }
}
