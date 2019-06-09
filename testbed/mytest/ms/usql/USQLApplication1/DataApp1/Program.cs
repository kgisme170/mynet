using System;

namespace DataApp1
{
    class Program
    {
        private Program()
        {
            var v = "ab";
            dynamic d = "ab";
            Console.WriteLine(v.Length + d.Length);

            DateTime dt = DateTime.Now;
            Console.WriteLine(dt == null);

        }
        static void Main(string[] args)
        {
            UseLinq.Test();
        }
    }
}
