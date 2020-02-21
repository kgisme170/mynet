using NUnit.Framework;
using System;

namespace NUnitTestProject_core
{
    class OverrideProperty
    {
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
        [Test]
        public static void TestOverrideProperty()
        {
            Derived d = new Derived
            {
                MI = 3,
                MJ = DateTime.Now
            };
            Assert.AreEqual(4, d.MI);
        }
    }
}
