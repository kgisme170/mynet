using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace UseDelegate
{
    public class Inner
    {
        public int M = 3;
        public async Task<IReadOnlyList<int>> F(IReadOnlyList<int> list)
        {
            Console.WriteLine(M);
            await Task.Delay(1000);
            return new List<int>() { 0 };
        }
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
            Func<IReadOnlyList<int>, Task<IReadOnlyList<int>>> f = i.F;
            var f1 = (Func<IReadOnlyList<int>, Task<IReadOnlyList<int>>>)i.F;
            Action g = i.G;
            f(new List<int>() { 0 });
            g();
            Caller(g);
            Console.WriteLine("Hello World!");
        }
    }
}
