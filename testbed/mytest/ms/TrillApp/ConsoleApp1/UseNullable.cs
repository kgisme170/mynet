using System;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApp1
{
    class Member
    {
        public string Name { get; set; }
    }
    class Inner
    {
        public Member mI { get; set; }
        public Member mJ { get; set; }
    }
    class Outter
    {
        public Inner inner { get; set; }
    }
    class UseNullable
    {
        static void Test()
        {
            Outter outter = new Outter();
            Console.WriteLine(outter?.inner?.mI?.Name);
            Console.WriteLine(outter?.inner == null);

            Console.WriteLine(outter?.inner?.mI == null ? "a" : "b");
            Console.WriteLine(new Guid());
        }
    }
}
