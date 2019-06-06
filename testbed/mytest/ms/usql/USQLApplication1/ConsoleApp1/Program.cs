using System;

namespace ConsoleApp1
{
    class Immutable // immutable object
    {
        public Immutable(string _cur, string _addr)
        {
            Currency = _cur;
            Address = _addr;
        }

        public string Currency { get; }
        public string Address { get; }
    }
    class Program
    {
        public static void Main(String [] args)
        {
            Guid guid = Guid.NewGuid();
            Console.WriteLine("_: " + guid);
            Console.WriteLine("B: " + guid.ToString("B"));
            Console.WriteLine("D: " + guid.ToString("D"));
            Console.WriteLine("N: " + guid.ToString("N"));

            var m = new Immutable("usd", "us");
            string s = m.Currency;
            Unsafe.Test();

            var u = new UseGeneric<int>();
            Console.WriteLine(u.Compare(1, 2));
        }
    }
}
