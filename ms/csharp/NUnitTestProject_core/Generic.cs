using NUnit.Framework;

namespace NUnitTestProject_core
{
    class Generic<UNKNOWN>
    {
        public bool Compare(UNKNOWN x, UNKNOWN y)
        {
            return x.Equals(y);
        }
        [Test]
        public static void Test()
        {
            var u = new Generic<int>();
            Assert.False(u.Compare(1, 2));
        }
    }
}
