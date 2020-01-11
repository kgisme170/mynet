using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class DeferExecution
    {
        public static int multi10(int n)
        {
            Console.WriteLine("multi10");
            return n * 10;
        }
        public static void Test()
        {
            var numbers = Enumerable.Range(2, 5).ToList();
            numbers.Print();

            var q1 = numbers.Select(multi10);
            numbers.AddRange(Enumerable.Range(12, 5));
            q1.Print(); // executes query
            q1.Print(); // executes query

            var l1 = q1.ToList(); // executes query
            l1.Print();
            l1.Print();

            int m = 10;
            var q = new int[] { 1, 2 }.Select(n => n * m);
            m = 20;
            q.Print(); // 20 40
        }
    }
}
