using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UseNullable
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
    class Program
    {
        static void Main(string[] args)
        {
            UseJoin.Test();
        }

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
