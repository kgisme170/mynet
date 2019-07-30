using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class CrossJoin
    {
        public static void Test()
        {
            string[] players = { "Tom", "Jay", "Mary" };
            IEnumerable<string> query = from name1 in players
                                        from name2 in players
                                        select name1 + " vs " + name2;
            query.Print();
        }
    }
}
