using System;
namespace ConsoleApp1
{
    class Coalescing
    {
        public static void Test()
        {
            string str1 = null;
            string str2 = null;
            string str3 = null;
            string str4 = "abc";
            string str5 = str1 ?? str2 ?? str3 ?? str4;
            Console.WriteLine(str5);
        }
    }
}
