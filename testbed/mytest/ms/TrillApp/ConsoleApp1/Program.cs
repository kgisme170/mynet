using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class Program
    {
        public static IEnumerable<int> MyFun(int i)
        {
            int counter = i;
            while (counter-- >0)
            {
                yield return counter;
            }
            yield return 10000;
        }
        public static void Test()
        {
            foreach (int i in MyFun(5))
            {
                Console.WriteLine(i);
            }
        }
        static void DoWork(int id, int sleep)
        {
            Console.WriteLine("Thread{0} begins", id);
            Thread.Sleep(sleep);
            Console.WriteLine("Thread{0} ends", id);
        }
        static void DoOtherWork(int id, int sleep)
        {
            Console.WriteLine("Other Thread{0} begins", id);
            Thread.Sleep(sleep);
            Console.WriteLine("Other Thread{0} ends", id);
        }
        static void Test2()
        {
            Task t1 = Task.Factory.StartNew(() => DoWork(1, 200)).ContinueWith((prev) => DoOtherWork(7, 300));
            Task t2 = Task.Factory.StartNew(() => DoWork(2, 200));
            Task t3 = Task.Factory.StartNew(() => DoWork(3, 200));

            var v1 = new Task(() => DoWork(4, 200));
            var v2 = new Task(() => DoWork(5, 200));
            var v3 = new Task(() => DoWork(6, 200));
            v1.Start();
            v2.Start();
            v3.Start();

            int[] arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
            Parallel.For(0, 10, i =>
            {
                Console.WriteLine("i=" + i);
                Thread.Sleep(1000);
            });
            Console.ReadKey();
        }
        static void Main(string[] args)
        {
        }
    }
}
