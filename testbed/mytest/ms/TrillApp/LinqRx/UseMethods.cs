using System;
using System.Collections.Generic;
using System.Linq;

namespace LinqRx
{
    class UseMethods
    {
        public static void Test()
        {
            int[] foo =
                (from n in Enumerable.Range(0, 10)
                 select n * n).ToArray();
            foo.Where(n => n > 50).Select(n => "n=" + n).ForAll(Console.WriteLine);
        }
    }
}
