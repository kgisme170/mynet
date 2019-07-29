using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class Content
    {
        public int id { get; set; }
        public string inst { get; set; }
        public string name { get; set; }
        public override string ToString()
        {
            //return $"{id}  | {inst}\t| {name}";
            return "id=" + id + ",inst=" + inst + ",name=" + name;
        }
    }
    class SimulatePartition
    {
        public static void Test()
        {
            var beatles = (new[]
            {
                new Content(){id = 1, inst = "guitar", name = "john"},
                new Content(){id = 2, inst = "guitar", name = "george"},
                new Content(){id = 3, inst = "guitar", name = "paul"},
                new Content(){id = 4, inst = "drums", name = "ringo"},
                new Content(){id = 5, inst = "drums", name = "pete"}
            });

            var result = beatles
                .GroupBy(g => g.inst)
                .Select(c => c.OrderBy(o => o.id).Select((v, i) => new { i, v }))
                .SelectMany(c => c)
                .Select(c => new { c.v.id, c.v.inst, c.v.name, rn = c.i + 1 })
                .ToList();

            Console.WriteLine("id | inst \t| name  \t| rn");
            foreach (var row in result)
            {
                Console.WriteLine($"{row.id}  | {row.inst}\t| {row.name}  \t| {row.rn}");
            }
            Console.WriteLine("---------------------------------------------");
            var groups = from b in beatles
                       group b by b.inst into g
                       select g;

            var r1 = from g in groups
                     from r in g
                     orderby r.id
                     select r;

            //var r2 = groups.Select(c => c.OrderBy(o => o.id).Select((v, i) => new { i, v }).ToList());
            var r2 = groups.Select(c => c.OrderBy(o => o.id).Select((content, index) => new { index, content }));
            foreach(var list in r2)
            {
                foreach(var line in list)
                {
                    Console.WriteLine(line.content.ToString() + ":" + line.index);
                }
            }

            Console.WriteLine("---------------------------------------------");
            var r3 = r2.SelectMany(c => c).Select(c => new { c.content.id, c.content.inst, c.content.name, rowNumber = c.index + 1 }).ToList();
            Console.WriteLine("id | inst \t| name  \t| rowNumber");
            foreach (var row in r3)
            {
                Console.WriteLine($"{row.id}  | {row.inst}\t| {row.name}  \t| {row.rowNumber}");
            }
            Console.WriteLine("---------------------------------------------");
            //var r2 = r1.Select((v, i) => new { i, v }.ToList());

            //var ret = r1.Select(c => c.Select((v, i) => new { i, v }).ToList());
            /*
                .SelectMany(c => c)
                .Select(c => new { c.v.id, c.v.inst, c.v.name, rn = c.i + 1 })
                .ToList();
            Console.WriteLine("id | inst \t| name  \t| rn");
            foreach (var row in ret)
            {
                Console.WriteLine($"{row.id}  | {row.inst}\t| {row.name}  \t| {row.rn}");
            }
            Console.WriteLine("----------\n");*/
        }
    }
}
