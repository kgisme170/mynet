using NUnit.Framework;
using System;
using System.ComponentModel;
using System.Threading;

namespace NUnitTestProject_core
{
    class Destructor
    {
        public static int i = 0;

        public static void Test()
        {
            using (C o = new C())
            {
            }

            C obj = new C();
            obj.F();
            obj = null;
            Console.WriteLine("set to null");
            Thread.Sleep(2000);
            Console.WriteLine("after sleep 1, begin GC");
            GC.Collect();
            GC.WaitForPendingFinalizers();
            Thread.Sleep(2000);
            Console.WriteLine("after sleep 2");
        }

        [Test]
        public static void TestDestructor()
        {
            Test();
            Assert.AreEqual(i, 111); // TODO seems in test project, destructors are not called.
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
            private IntPtr handle;
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

                    // Call the appropriate methods to clean up
                    // unmanaged resources here.
                    // If disposing is false,
                    // only the following code is executed.
                    CloseHandle(handle);
                    handle = IntPtr.Zero;

                    // Note disposing has been done.
                    disposed = true;
                }
            }
            [System.Runtime.InteropServices.DllImport("Kernel32")]
            private extern static Boolean CloseHandle(IntPtr handle);

            public void F() { Console.WriteLine("start"); }
        }
    }
}
