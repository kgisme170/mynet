using NUnit.Framework;

namespace NUnitTestProject_core
{
    class Indexer
    {
        class C
        {
            public int this[int i]
            {
                get
                {
                    return i + 1;
                }
            }
        }
        [Test]
        public static void TestIndexer()
        {
            Assert.AreEqual(4, new C()[3]);
        }
    }
}
