using System;

namespace ConsoleApp1
{
    public class My
    {
        public int F() { return 3; }
        public int MI { get; set; }
    }
    public static class Ex
    {
        public static int G(this My obj, int i)
        {
            return obj.MI + i;
        }
    }
    class Extension
    {
        static void Test()
        {
            byte[] pv = null;
            Console.WriteLine(pv);
            String s = "test" + pv;
            Console.WriteLine(s);

            My m = new My();
            Console.WriteLine(m.G(3));
        }
    }
}
