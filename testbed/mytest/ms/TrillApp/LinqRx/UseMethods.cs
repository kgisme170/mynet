using System;
using System.Linq;

namespace LinqRx
{
    class UseMethods
    {
        public static void Test()
        {
            int[] foo =
                (from n in Enumerable.Range(0, 100)
                 select n * n).ToArray();
            foo
                .Where(n => n > 50 && n < 300)
                .Select(n => "n=" + n)
                .Take(5)
                .Skip(1)
                .ForAll(Console.WriteLine);
        }
    }
}
