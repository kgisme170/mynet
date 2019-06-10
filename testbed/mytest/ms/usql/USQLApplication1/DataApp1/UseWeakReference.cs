using System;
using System.Threading;

namespace DataApp1
{
    class Person
    {
        private int mI = 3;
        public int MI { get => mI; set => mI = value; }
    }
    class UseWeakReference
    {
        public static void Test()
        {
            Person person = new Person();
            WeakReference<Person> wr = new WeakReference<Person>(person);

            wr.TryGetTarget(out Person p1);
            Console.WriteLine(p1);

            person = null;
            wr.TryGetTarget(out Person p2);
            Console.WriteLine(p2);

            p2 = null;
            System.GC.Collect();
            Thread.Sleep(1000);
            wr.TryGetTarget(out Person p3);
            Console.WriteLine(p3); // I expected null here becaure person is collected.
        }
    }
}
