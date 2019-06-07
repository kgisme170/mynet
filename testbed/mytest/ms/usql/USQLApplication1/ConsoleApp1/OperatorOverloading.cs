namespace ConsoleApp1
{
    class Class1
    {
        int mI = 0;
        string mS = "ab";

        public static Class1 operator + (Class1 obj1, Class1 obj2)
        {
            return new Class1()
            {
                mI = obj1.mI + obj2.mI,
                mS = obj1.mS + obj2.mS
            };
        }
    }
    class OperatorOverloading
    {
        public static void Test()
        {
            Class1 obj1 = new Class1();
            Class1 obj2 = new Class1();
            Class1 obj3 = obj1 + obj2;
        }
    }
}
