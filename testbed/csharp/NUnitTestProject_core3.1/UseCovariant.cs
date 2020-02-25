using NUnit.Framework;
using System.Collections.Generic;

namespace NUnitTestProject_core
{
    class UseCovariant
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
                return default;
            }
        }
        [Test]
        public static void TestUseCovariant()
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
