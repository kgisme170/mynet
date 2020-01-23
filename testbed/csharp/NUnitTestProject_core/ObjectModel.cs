using NUnit.Framework;

namespace NUnitTestProject_core
{
    class ObjectModel
    {
        class Base
        {
            public virtual int F(int i)
            {
                return 1;
            }
        }
        class Derive : Base
        {
            public double F(double d)
            {
                return 2.0;
            }
        }
        [Test]
        public static void FragileClass()
        {
            Derive d = new Derive();
            Assert.AreEqual(2.0, d.F(2));
        }

        [Test]
        public static void ObjectEqual()
        {
            object o1 = "hello";
            object o2 = new string("hello".ToCharArray());
            Assert.False(o1 == o2);
            Assert.True(o1.Equals(o2));

            string s1 = "hello";
            string s2 = new string("hello".ToCharArray());
            Assert.True(s1 == s2);
            Assert.True(s1.Equals(s2));
        }
    }
}
