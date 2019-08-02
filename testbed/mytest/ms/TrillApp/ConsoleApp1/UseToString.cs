using System;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApp1
{
    class UseToString
    {
        public static void Test()
        {
            Guid guid = Guid.NewGuid();
            Console.WriteLine(guid);
            Console.WriteLine(guid.ToString("N"));
            Console.WriteLine(guid.ToString("D"));

            Guid? guid2 = Guid.NewGuid();
            Console.WriteLine(guid2.Value.ToString("N"));
        }
    }
}
