using NUnit.Framework;
namespace NUnitTestProject_core
{
    class C
    {
        readonly int mI = 0;
        string mS = "ab";
        public C(int i)
        {
            mI = i;
        }
        public int GetI()
        {
            return mI;
        }
        public static C operator +(C obj1, C obj2)
        {
            return new C(obj1.mI + obj2.mI)
            {
                mS = obj1.mS + obj2.mS
            };
        }
    }
    class OperatorOverloading
    {
        [Test]
        public static void TestOperatorOverloading()
        {
            C obj1 = new C(1);
            C obj2 = new C(2);
            C obj3 = obj1 + obj2;
            Assert.AreEqual(3, obj3.GetI());
        }
    }
}
