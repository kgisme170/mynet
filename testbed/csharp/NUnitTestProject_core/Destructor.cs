using NUnit.Framework;
using System;
using System.ComponentModel;
using System.Threading;

namespace NUnitTestProject_core
{
    class Destructor
    {
        public static int i = 0;
        [Test]
        public static void TestDestructor()
        {
            using (C o = new C())
            {
            }
            Assert.AreEqual(0, i);
        }
        class A
        {
            ~A()
            {
                i += 1;
                Console.WriteLine("~A");
            }
        }
        class B : A
        {
            ~B()
            {
                i += 10;
                Console.WriteLine("~B");
            }
        }
        class C : B, IDisposable
        {
            ~C()
            {
                i += 100;
                Console.WriteLine("~C");
                Dispose(false);
            }

            public void Dispose()
            {
                Dispose(true);
                GC.SuppressFinalize(this);
            }
            private bool disposed = false;
            private Component component = new Component();
            protected virtual void Dispose(bool disposing)
            {
                Console.WriteLine("Dispose:" + disposing);
                // Check to see if Dispose has already been called.
                if (!disposed)
                {
                    // If disposing equals true, dispose all managed
                    // and unmanaged resources.
                    if (disposing)
                    {
                        // Dispose managed resources.
                        component.Dispose();
                    }
                    // Note disposing has been done.
                    disposed = true;
                }
            }
            public void F() { Console.WriteLine("start"); }
        }
    }
}
