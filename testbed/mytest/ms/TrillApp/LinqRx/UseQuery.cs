using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    public static class Extensions
    {
        public static void ForAll<T>(this IEnumerable<T> seq, Action<T> act)
        {
            foreach (T t in seq)
            {
                act(t);
            }
        }
    }
    class UseQuery
    {
        public static void Test()
        {
            int[] foo = //new int[100];
                (from n in Enumerable.Range(0, 100)
                 select n * n).ToArray();
            foreach(int i in foo) { Console.WriteLine(i); }
            foo.ForAll(Console.WriteLine);

            Produce1().ForAll(Console.Write);
            Console.WriteLine();
            Console.WriteLine();
            Produce2().ForAll(Console.Write);
            Console.WriteLine();
            Console.WriteLine();
            Produce3().ForAll(Console.Write);
            Console.WriteLine();
            Console.WriteLine();
            Produce4().ForAll(Console.Write);
            Console.WriteLine();
            Console.WriteLine();
            Produce5().ForAll(Console.Write);
            Console.WriteLine();

            Enumerable.Range(1, 15).Where(i => i % 2 == 0).ForAll(Console.Write);
            Console.WriteLine();
        }

        public static IEnumerable<Tuple<int, int>> Produce1()
        {
            for(int x = 0; x < 10; ++x)
            {
                for (int y = 0; y < 10; ++y)
                {
                    if (x + y < 15)
                    {
                        yield return Tuple.Create(x, y);
                    }
                }
            }
        }

        public static IEnumerable<Tuple<int, int>> Produce2()
        {
            return
                from x in Enumerable.Range(11, 10)
                from y in Enumerable.Range(11, 10)
                where x + y < 35
                select Tuple.Create(x, y);
        }

        public static IEnumerable<Tuple<int, int>> Produce3()
        {
            var storage = new List<Tuple<int, int>>();
            for (int x = 0; x < 10; ++x)
            {
                for (int y = 0; y < 10; ++y)
                {
                    if (x + y < 15)
                    {
                        storage.Add(Tuple.Create(x, y));
                    }
                }
            }
            storage.Sort((p1, p2) =>
                (p2.Item1 * p2.Item1 + p2.Item2 * p2.Item2).CompareTo(p1.Item1 * p1.Item1 + p1.Item2 * p1.Item2)
            );
            return storage;
        }

        public static IEnumerable<Tuple<int, int>> Produce4()
        {
            return
                from x in Enumerable.Range(11, 10)
                from y in Enumerable.Range(11, 10)
                where x + y < 35
                orderby (x * x + y * y) descending
                select Tuple.Create(x, y);
        }

        public static IEnumerable<Tuple<int, int>> Produce5()
        {
            return
                Enumerable.Range(1, 10)
                .SelectMany(
                    x => Enumerable.Range(1, 10),
                    (x, y) => Tuple.Create(x, y)
                )
                .Where(pt => pt.Item1 + pt.Item2 < 10)
                .OrderByDescending(pt => pt.Item1 * pt.Item1 + pt.Item2 + pt.Item2);
        }
    }
}
