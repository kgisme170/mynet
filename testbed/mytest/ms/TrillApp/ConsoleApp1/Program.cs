using System;
using System.Collections.Generic;

namespace ConsoleApp1
{
    class Program
    {
        public static IEnumerable<int> MyFun(int i)
        {
            int counter = i;
            while (counter-- >0)
            {
                yield return counter;
            }
            yield return 10000;
        }
        public static void Test()
        {
            foreach (int i in MyFun(5))
            {
                Console.WriteLine(i);
            }
        }

        static void Main(string[] args)
        {
            IList<string> list = new List<string>();
        }
    }
}
