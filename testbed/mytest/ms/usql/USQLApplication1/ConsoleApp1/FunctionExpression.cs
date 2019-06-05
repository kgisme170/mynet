using System;
using System.Linq.Expressions;

namespace ConsoleApp1
{
    class FunctionExpression
    {
        delegate void vFunc();
        static readonly vFunc mFunc = Vf;
        public static void Vf()
        {
            Console.WriteLine("func");
        }

        delegate double dFunc(int r);
        static readonly dFunc aFunc = Area;
        public static double Area(int r)
        {
            return 3.14 * r * r;
        }

        static void Test()
        {
            mFunc();
            Console.WriteLine(aFunc(3));

            dFunc func = new dFunc(Area);
            Console.WriteLine(func(4));
            dFunc circumference = new dFunc(delegate (int r) { return 6.28 * r; });
            Console.WriteLine(circumference(5));

            dFunc circ = r => 6.28 * r;
            Console.WriteLine(circ(8));
            Func<double, double> f = r => 6.28 * r;
            Console.WriteLine(f(9));

            void ai(int _x) => Console.WriteLine(_x + 1);
            ai(5);

            Action<int> a = ai;
            a(5);

            Action av = () => Console.WriteLine("av");
            av();

            Predicate<int> p = _x => _x == 3;
            Console.WriteLine(p(4));

            string date = "2019-05-27 00:00:00";
            DateTime time = DateTime.Parse(date);
            Console.WriteLine(time);
            DateTime t1 = time.AddHours(23);
            string s = t1.ToString();
            Console.WriteLine(s);

            int x = 3, y = 4;
            BinaryExpression b1 = Expression.MakeBinary(ExpressionType.Add,
                Expression.Constant(1), Expression.Constant(2));
            BinaryExpression b2 = Expression.MakeBinary(ExpressionType.Add,
                Expression.Constant(x), Expression.Constant(y));

            BinaryExpression b3 = Expression.MakeBinary(ExpressionType.Subtract, b1, b2);
            int result = Expression.Lambda<Func<int>>(b3).Compile()();
            Console.WriteLine(result);
        }
    }
}
