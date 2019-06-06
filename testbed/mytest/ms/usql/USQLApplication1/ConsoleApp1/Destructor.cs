using System;
using System.ComponentModel;
using System.Threading;
namespace ConsoleApp1
{
    class A
    {
        ~A() { Console.WriteLine("~A"); }
    }
    class B:A
    {
        ~B() { Console.WriteLine("~B"); }
    }
    class C : B, IDisposable
    {
        ~C() {
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
    class Destructor
    {
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
            Thread.Sleep(2000);
            Console.WriteLine("after sleep 2");
        }
    }
}
