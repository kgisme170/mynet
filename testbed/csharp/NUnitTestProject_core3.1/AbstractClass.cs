using NUnit.Framework;

namespace NUnitTestProject_core
{
    abstract class Ac
    {
        private int _mi;

        public int MI { get => _mi; set => _mi = value; }

        public abstract void f();
    }
    class Derive : Ac
    {
        public override void f() { }
    }

    class AbstractClass
    {
        [Test]
        public static void TestAbstractClass()
        {
            Ac o = new Derive
            {
                MI = 3
            };
            Assert.Equals(3, o.MI);
        }
    }
}
