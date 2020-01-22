using System;
namespace ConsoleApp1
{
    class GuidFormatter
    {
        public static void Test()
        {
            Guid guid = Guid.NewGuid();
            Console.WriteLine("_: " + guid);
            Console.WriteLine("B: " + guid.ToString("B"));
            Console.WriteLine("D: " + guid.ToString("D"));
            Console.WriteLine("N: " + guid.ToString("N"));
        }
    }
}
