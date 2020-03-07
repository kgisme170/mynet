using NUnit.Framework;
using System;

namespace NUnitTestProject_standard2
{
    public class TestInterface
    {
        interface IMy
        {
            void f();
        }

        interface IYou
        {
            void f();
        }

        class My01 : IMy, IYou
        {
            public void f() { Console.WriteLine("My01"); }
        }

        class My02 : IMy, IYou
        {
            void IMy.f() { Console.WriteLine("My02"); }
            void IYou.f() { Console.WriteLine("You02"); }
        }

        [Test]
        public void TestNullIReadOnlyList()
        {
            Assert.Pass();
            var my = new My01();
            my.f();
            IMy im = my;
            im.f();

            var m = new My02();
            //m.f();
            IMy i01 = (IMy)m;
            i01.f();
            IYou i02 = (IYou)m;
            i02.f();

            Environment.Exit(0);

            int?[] data = { 1, null, 18, 22, 255 };
            var result = data.OfType<int>();
        }
    }
}