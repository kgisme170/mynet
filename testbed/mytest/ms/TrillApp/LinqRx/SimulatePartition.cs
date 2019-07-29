using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class SimulatePartition
    {
        public static void Test()
        {
            var beatles = (new[]
            {
                new {id = 1, inst = "guitar", name = "john"},
                new {id = 2, inst = "guitar", name = "george"},
                new {id = 3, inst = "guitar", name = "paul"},
                new {id = 4, inst = "drums", name = "ringo"},
                new {id = 5, inst = "drums", name = "pete"}
            });

            var result = beatles
                .GroupBy(g => g.inst)
                .Select(c => c.OrderBy(o => o.id).Select((v, i) => new { i, v }).ToList())
                .SelectMany(c => c)
                .Select(c => new { c.v.id, c.v.inst, c.v.name, rn = c.i + 1 })
                .ToList();

            Console.WriteLine("id | inst \t| name  \t| rn");
            foreach (var row in result)
            {
                Console.WriteLine($"{row.id}  | {row.inst}\t| {row.name}  \t| {row.rn}");
            }
        }
    }
}
