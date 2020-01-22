using System;
using System.Collections.Generic;
using System.Configuration;

namespace ConsoleApp1
{
    class IterateYield
    {
        public static int Sum(params int[] nums)
        {
            int s = 0;
            foreach (int n in nums)
            {
                s += n;
            }
            return s;
        }

        public static readonly double PI = Convert.ToDouble(ConfigurationManager.AppSettings[0]);

        public static IEnumerable<int> Filter(IEnumerable<int> ii)
        {
            foreach (var i in ii)
            {
                if (i > 2)
                {
                    yield return i;
                }
            }
        }

        public static IEnumerable<int> RunningTotal(IEnumerable<int> ii)
        {
            int t = 0;
            foreach (var i in ii)
            {
                t += i;
                yield return t;
            }
        }
        public static void Test(string[] args)
        {
            List<int> li = new List<int>()
            {
                1,2,3,4,5
            };
            foreach (var k in Filter(li))
            {
                Console.WriteLine(k);
            }
            Console.WriteLine("--------");
            foreach (var k in RunningTotal(li))
            {
                Console.WriteLine(k);
            }
            Console.WriteLine("--------");
            IEnumerable<int> iei = li;
            foreach (var j in li)
            {
                Console.WriteLine(j);
            }
            IEnumerator<int> ei = li.GetEnumerator();
            while (ei.MoveNext())
            {
                Console.WriteLine(ei.Current);
            }
            //AnonymousTypes.Test();
            object o1 = "hello";
            object o2 = new string("hello".ToCharArray());
            Console.WriteLine(o1 == o2);
            Console.WriteLine(o1.Equals(o2));

            string s1 = "hello";
            string s2 = new string("hello".ToCharArray());
            Console.WriteLine(s1 == s2);
            Console.WriteLine(s1.Equals(s2));

            int? i = null;
            Console.WriteLine(i.HasValue);
            try
            {
                Console.WriteLine(i.Value);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            int x1 = int.Parse("123");
            Console.WriteLine(PI);
            Console.WriteLine(5 + Convert.ToInt32("5"));

            int x = 2147383647; // int.MaxValue;
            int y = 2147383647; // int.MaxValue;
            int z = x + y;// checked(x + y);

            Console.WriteLine(Sum(1, 2, 3));
        }
    }
}
