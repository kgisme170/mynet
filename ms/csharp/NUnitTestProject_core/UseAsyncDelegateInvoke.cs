using NUnit.Framework;
using System;
using System.Threading;

namespace NUnitTestProject_core
{
    class UseAsyncDelegateInvoke
    {
        class AsyncDemo
        {
            public string TestMethod(int callTime)
            {
                Console.WriteLine("async begins");
                Thread.Sleep(callTime);
                return "Method cost time" + callTime / 1000 + "s";
            }
        }
        [Test]
        public static void Test()
        {
            AsyncDemo ad = new AsyncDemo();
            AsyncDelegate dlgt = new AsyncDelegate(ad.TestMethod);
            object[] obj = { dlgt, 12 };
            dlgt.BeginInvoke(3000, new AsyncCallback(CallbackMethod), obj);
            Console.WriteLine("main thread begins sleep");
            Thread.Sleep(1000);
            Console.WriteLine("main thread ends sleep");
            Console.ReadKey();
        }
        public delegate string AsyncDelegate(int callTime);
        static void CallbackMethod(IAsyncResult ar)
        {
            object[] obj = (object[])ar.AsyncState;
            AsyncDelegate dlgt = (AsyncDelegate)obj[0];
            int iTest = (int)obj[1];
            string result = dlgt.EndInvoke(ar);
            Console.WriteLine("async finishes，{0}！", result);
            Console.WriteLine("ar.AsyncState value:{0}", iTest);
        }
    }
}
