using System;
using System.Collections.Generic;
using System.Configuration;

namespace ConsoleApp1
{
    class IterateYield
    {
        public static readonly double PI = Convert.ToDouble(ConfigurationManager.AppSettings[0]);

        public static void Test(string[] args)
        {

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
