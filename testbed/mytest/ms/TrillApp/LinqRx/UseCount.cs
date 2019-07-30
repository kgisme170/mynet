using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class UseCount
    {
        public static void Test()
        {
            var v = new[]
            {
                new {Id = 1, Name = "abc" },
                new {Id = 2, Name = "xyz" },
                new {Id = 3, Name = "123" },
                new {Id = 4, Name = "456" },
                new {Id = 5, Name = "mod" },
                new {Id = 6, Name = "uio" },
                new {Id = 7, Name = "abc" },
                new {Id = 1, Name = "abc" }
            };
            var c = v.Count();
            c.Print();

            var l = from e in v
                    group e by e.Id into eg
                    select new { k = eg.Key, CustCount = eg.Count() };
            l.Print();

            var d = from e in l
                    where e.CustCount > 1
                    select e;
            d.Print();
        }
    }
}
