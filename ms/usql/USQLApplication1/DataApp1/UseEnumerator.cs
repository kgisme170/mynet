using System;
using System.Collections.Generic;
using System.Linq;
using System.Reactive.Linq;

namespace DataApp1
{
    class UseEnumerator
    {
        public static void Test()
        {
            var buf = new int[] { 1, 2, 3, 4, 5 };
            IEnumerable<int> ii = buf;
            var it = buf.GetEnumerator();
            it.MoveNext();

            Console.WriteLine(it.Current);
            foreach(int i in ii)
            {
                Console.Write(i);
            }
            Console.WriteLine("\n----ToObservable----");
            buf.ToObservable().Subscribe(Console.Write);
            Console.WriteLine("\n----Select int to boolean----");
            buf.ToObservable().Select(x => x >= 3).Subscribe(Console.Write);

            Console.WriteLine("\n----Filter numbers----");
            buf.ToObservable().Where(x => x >= 3).Subscribe(Console.Write);
        }
    }
}