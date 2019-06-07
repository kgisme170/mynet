using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            var v = "ab";
            dynamic d = "ab";
            Console.WriteLine(v.Length +　d.Length);

            DateTime dt = DateTime.Now;
            Console.WriteLine(dt == null);
            FragileClass.Test();
        }
    }
}
