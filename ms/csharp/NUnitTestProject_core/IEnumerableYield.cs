using NUnit.Framework;
using System.Collections.Generic;

namespace NUnitTestProject_core
{
    class IEnumerableYield
    {
        public static IEnumerable<int> MyFun(int i)
        {
            int counter = i;
            while (counter-- > 0)
            {
                yield return counter;
            }
            yield return 10000;
        }
        [Test]
        public static void Test()
        {
            int sum = 0;
            foreach (int i in MyFun(5))
            {
                sum += i;
            }
            Assert.AreEqual(10010, sum);
        }
    }
}
