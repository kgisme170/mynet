using System;
using System.Configuration;

namespace ConsoleApp1
{
    class Program
    {
        public static readonly double PI = Convert.ToDouble(ConfigurationManager.AppSettings[0]);
        public static void Main(string[] args)
        {
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
            catch (Exception e){
                Console.WriteLine(e.Message);
            }
            int x = int.Parse("123");
            Console.WriteLine(PI);
        }
    }
}
