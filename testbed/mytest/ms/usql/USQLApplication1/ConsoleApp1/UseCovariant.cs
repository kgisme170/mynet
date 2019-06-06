using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class Animal
    {

    }
    class Dog : Animal
    {

    }

    interface IMyEnumberable<out T>
    {
        T GetDefault();
    }
    class MyList<T> : IMyEnumberable<T>
    {
        public T GetDefault()
        {
            return default(T);
        }
    }
    class UseCovariant
    {
        public static void Test()
        {
            List<Dog> ld = new List<Dog>();
            IEnumerable<Dog> it = ld;
            IEnumerable<Animal> ia = it;

            MyList<Dog> md = new MyList<Dog>();
            IMyEnumberable<Dog> id = md;
            IMyEnumberable<Animal> ma = id;
        }
    }
}
