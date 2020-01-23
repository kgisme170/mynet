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

        public static IEnumerable<int> Filter(IEnumerable<int> li)
        {
            foreach (var i in li)
            {
                if (i > 2)
                {
                    yield return i;
                }
            }
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
            sum = 0;
            foreach (int i in Filter(new List<int>{1,2,3}))
            {
                sum += i;
            }
            Assert.AreEqual(3, sum);
        }
    }
}
