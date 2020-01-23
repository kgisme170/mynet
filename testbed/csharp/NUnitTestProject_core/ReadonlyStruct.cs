using NUnit.Framework;
using System.ComponentModel;

namespace NUnitTestProject_core
{
    struct MyStruct
    {
        public int mI;
        public string mS;
    }
    [ImmutableObject(true)]
    readonly struct ComplexStruct
    {
        public int MJ { get; }
        public readonly MyStruct st;
        public ComplexStruct(int mj, MyStruct s)
        {
            MJ = mj;
            st = s;
        }
    }
    class ReadonlyStruct
    {
        [Test]
        public static void Test()
        {
            ComplexStruct cs = new ComplexStruct
            (
                6,
                new MyStruct
                {
                    mI = 7,
                    mS = "immutable"
                }
            );
            Assert.AreEqual(7, cs.st.mI);
        }
    }
}
