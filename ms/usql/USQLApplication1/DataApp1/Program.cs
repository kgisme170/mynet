#pragma warning disable CS0414
namespace DataApp1
{
    class NotUsed
    {
        private readonly int pi = 1;
        private readonly int pj = 2;
    }
    class Program
    {
        static void Main(string[] args)
        {
            UseObservable.Test();
        }
    }
}