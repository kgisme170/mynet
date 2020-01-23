using System;
using System.ComponentModel;

namespace NUnitTestProject_core
{
    struct MyStruct
    {
        public int mI;
        public string mS;
    }
    [ImmutableObject(true)]
    struct ComplexStruct
    {
        public int MJ { get; set; }
        public MyStruct st;
    }
    class UseImmutableObject
    {
        public static void Test()
        {
            ComplexStruct cs = new ComplexStruct
            {
                MJ = 6,
                st = new MyStruct
                {
                    mI = 7,
                    mS = "immutable"
                }
            };
            cs.MJ = 7;
            cs.st.mS = "Changed";

            cs = new ComplexStruct
            {
                MJ = 8,
                st = new MyStruct
                {
                    mI = 9,
                    mS = "changed?"
                }
            };
            Console.WriteLine(cs.st.mS);
            cs.st = new MyStruct
            {
                mI = 8,
                mS = "check immutable?"
            };
        }
    }
}
