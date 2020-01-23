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
            int x = 2147383647; // int.MaxValue;
            int y = 2147383647; // int.MaxValue;
            int z = x + y;// checked(x + y);

            Console.WriteLine(Sum(1, 2, 3));
        }
    }
}
