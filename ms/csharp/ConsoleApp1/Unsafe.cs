using System;
namespace ConsoleApp1
{
    struct Myclass
    {
        public int val;
    }
    class Unsafe
    {
        unsafe public static UInt16 Htons(UInt16 src)
        {
            UInt16 dest;
            ((byte*)&dest)[0] = ((byte*)&src)[1];
            ((byte*)&dest)[1] = ((byte*)&src)[0];
            return dest;
        }

        public static void Test()
        {
            UInt16 val = 1;
            unsafe
            {
                int* iArray = stackalloc int[100];
                val = Htons(val);

                Myclass* pm = stackalloc Myclass[1];
                pm[0].val = 3;
            }
            Console.WriteLine(val);
        }
    }
}
