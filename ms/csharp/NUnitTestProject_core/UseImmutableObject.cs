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
        public int mJ;
        public MyStruct st;
    }
    class UseImmutableObject
    {
        public static void Test()
        {
            MyStruct s1;
            s1.mI = 1;
            s1.mS = "ab";

            MyStruct s2 = new MyStruct
            {
                mI = 2,
                mS = "xy"
            };

            MyStruct s3 = s1;
            s3.mI = 4;
            MyStruct s4 = s2;
            s4.mI = 5;
            Console.WriteLine(s1.mI);
            Console.WriteLine(s2.mI);

            ComplexStruct cs = new ComplexStruct
            {
                mJ = 6,
                st = new MyStruct
                {
                    mI = 7,
                    mS = "immutable"
                }
            };
            // TODO why can still change?
            cs.mJ = 7;
            cs.st.mS = "Changed";

            cs = new ComplexStruct
            {
                mJ = 8,
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
