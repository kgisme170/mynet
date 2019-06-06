using System;
using System.Threading;

namespace ConsoleApp1
{
    class ResetEvent
    {
        static AutoResetEvent e = new AutoResetEvent(false);
        //static ManualResetEvent e = new ManualResetEvent(false);//signal all the WaitOne() calls
        public static void Main(string[] args)
        {
            Thread t = new Thread(Method);
            t.Start();
            Console.ReadLine();
            e.Set();
            Console.ReadLine();
            e.Set();
            Coalescing.Test();
        }
        public static void Method()
        {
            Console.WriteLine("s1");
            e.WaitOne();
            Console.WriteLine("e1");
            Console.WriteLine("s2");
            e.WaitOne();
            Console.WriteLine("e2");
        }
    }
}
