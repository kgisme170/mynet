using NUnit.Framework;
using System;
using System.Threading;

namespace NUnitTestProject_core
{
    class Person
    {
        public int MI { get; set; } = 3;
    }
    class UseWeakReference
    {
        [Test]
        public static void Test()
        {
            Person person = new Person();
            WeakReference<Person> wr = new WeakReference<Person>(person);

            wr.TryGetTarget(out Person p1);
            Console.WriteLine(p1);

            person = null;
            wr.TryGetTarget(out Person p2);
            Console.WriteLine(p2);

            p1 = null;
            p2 = null;
            System.GC.Collect();
            Thread.Sleep(1000);
            wr.TryGetTarget(out Person p3);
            Assert.AreEqual(3, p3.MI);
        }
    }
}
