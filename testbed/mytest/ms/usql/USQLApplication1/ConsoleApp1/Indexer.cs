using System;

namespace ConsoleApp1
{
    class Indexer
    {
        class Math
        {
            public int this[int i]
            {
                get
                {
                    return i + 1;
                }
            }
        }
        class Base
        {
            protected int mI;
            protected long mJ;
            public int MI { get => mI; set => mI = value; }
            public long MJ { get => mJ; set => mJ = value; }
        }
        class Derived : Base
        {
            DateTime dt;
            public new int MI { get => mI; set => mI = value + 1; }
            public new DateTime MJ { get => dt; set => dt = value; }
        }
        static void Test(string[] args)
        {
            Console.WriteLine(new Math()[3]);
            Derived d = new Derived
            {
                MI = 3,
                MJ = DateTime.Now
            };
        }
    }
}
