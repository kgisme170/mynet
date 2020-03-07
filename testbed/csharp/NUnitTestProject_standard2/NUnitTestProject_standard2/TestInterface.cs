using NUnit.Framework;
using System;
using System.Linq;

namespace NUnitTestProject_standard2
{
    public class TestInterface
    {
        interface IMy
        {
            void F();
        }

        interface IYou
        {
            void F();
        }

        class My01 : IMy, IYou
        {
            public void F() { Console.WriteLine("My01"); }
        }

        class My02 : IMy, IYou
        {
            void IMy.F() { Console.WriteLine("My02"); }
            void IYou.F() { Console.WriteLine("You02"); }
        }

        [Test]
        public void TestNullIReadOnlyList()
        {
            Assert.Pass();
            var my = new My01();
            my.F();
            IMy im = my;
            im.F();

            var m = new My02();
            //m.f();
            IMy i01 = (IMy)m;
            i01.F();
            IYou i02 = (IYou)m;
            i02.F();

            Environment.Exit(0);

            int?[] data = { 1, null, 18, 22, 255 };
            var result = data.OfType<int>();
            foreach (var r in result)
            {
                Console.WriteLine(r);
            }
        }
    }
}