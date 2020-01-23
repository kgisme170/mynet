using System.Threading.Tasks;

namespace ConsoleApp1
{
    class Tpl
    {
        public static void Test()
        {
            Parallel.For(0, 100, x => Run());
        }
        private static void Run()
        {
            string x = "";
            for(int i = 0; i < 10000; ++i)
            {
                x = x + "s";
            }
        }
    }
}
