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
        public static void TestUseWeakReference()
        {
            Person person = new Person();
            WeakReference<Person> wr = new WeakReference<Person>(person);

            wr.TryGetTarget(out Person? p1);
            Console.WriteLine(p1);
            wr.TryGetTarget(out Person? p2);
            Console.WriteLine(p2);
            GC.Collect();
            Thread.Sleep(1000);
            if (wr.TryGetTarget(out Person? p3))
            {
                Assert.AreEqual(3, p3.MI);
            }
        }
    }
}
