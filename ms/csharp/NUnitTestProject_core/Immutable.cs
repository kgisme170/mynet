using NUnit.Framework;

namespace NUnitTestProject_core
{
    [TestFixture("", "")]
    class Immutable
    {
        public Immutable(string _cur, string _addr)
        {
            Currency = _cur;
            Address = _addr;
        }

        public string Currency { get; }
        public string Address { get; }

        [Test]
        public static void Test()
        {
            var m = new Immutable("usd", "us");
            string s = m.Currency;
            Assert.AreEqual("usd", s);
        }
    }
}
