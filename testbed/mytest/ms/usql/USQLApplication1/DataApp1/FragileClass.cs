using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataApp1
{
    class Base
    {
        public virtual void F(int i)
        {
            Console.WriteLine("Base F");
        }
    }
    class Derive : Base
    {
        public void F(double d)
        {
            Console.WriteLine("Derive F");
        }
    }
    class FragileClass
    {
        public static void Test()
        {
            Derive d = new Derive();
            d.F(2);
        }
    }
}
