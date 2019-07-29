using System;
using System.Collections.Generic;

namespace LinqRx
{
    public static class MyExtension
    {
        public static void Print<T>(this IEnumerable<T> data)
        {
            foreach (T t in data)
            {
                Console.WriteLine(t);
            }
            Console.WriteLine("----------");
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            //UseMethods.Test();
            //UseExpression.Test();
            UseSubquery.Test();
            //SimulatePartition.Test();
            //OrderBySelect.Test();
        }
    }
}
