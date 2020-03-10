using System;

namespace UseDelegate
{
    public class Inner
    {
        public int M = 3;
        public int F() { Console.WriteLine("f"); return 0; }
        public void G() { Console.WriteLine("g"); }
    }
    class Program
    {
        public static void Caller(Action a)
        {
            a();
        }
        static void Main(string[] args)
        {
            var i = new Inner();
            Func<int> f = i.F;
            Action g = i.G;
            f();
            g();
            Program.Caller(g);
            Console.WriteLine("Hello World!");
        }
    }
}
