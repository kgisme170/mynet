using NUnit.Framework;

namespace NUnitTestProject_core
{
    class Coalescing
    {
        [Test]
        public static void TestCoalescing()
        {
            string str1 = null;
            string str2 = null;
            string str3 = null;
            string str4 = "abc";
            string str5 = str1 ?? str2 ?? str3 ?? str4;
            Assert.True(str5.Equals(str4));
        }
    }
}
