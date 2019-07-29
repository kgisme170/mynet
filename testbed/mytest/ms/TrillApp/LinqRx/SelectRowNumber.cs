using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class OrderBySelect
    {
        public static void Test()
        {
            string[] fruits = { "apple", "banana", "mango", "orange", "passionfruit", "grape" };

            var f = fruits.Select((fruit, index) =>
                          new { index, str = fruit.Substring(0, index) });
            f.Print();
            f.Print();
        }
    }
}
