using NUnit.Framework;
using System;
using System.Linq.Expressions;

namespace NUnitTestProject_core
{
    class FunctionExpression
    {
        delegate int vFunc();
        static readonly vFunc mFunc = Vf;
        public static int Vf()
        {
            return 2;
        }

        delegate int dFunc(int r);
        static readonly dFunc aFunc = Area;
        public static int Area(int r)
        {
            return r * r;
        }
        [Test]
        public static void Test()
        {
            Assert.AreEqual(2, mFunc());
            Assert.AreEqual(9, aFunc(3));

            dFunc func = new dFunc(Area);
            Console.WriteLine(func(4));
            dFunc circumference = new dFunc(delegate (int r) { return 6 * r; });
            Assert.AreEqual(30, circumference(5));

            dFunc circ = r => 6 * r;
            Assert.AreEqual(48, circ(8));
            Func<double, double> f = r => 6 * r;
            Assert.AreEqual(54, f(9));

            int ai(int _x) => _x + 1;
            Assert.AreEqual(6, ai(5));

            // Action<int> a = ai;
            // Assert.AreEqual(6, a(5));

            Action av = () => Console.WriteLine("av");
            av();

            Predicate<int> p = _x => _x == 3;
            Assert.False(p(4));

            int x = 3, y = 4;
            BinaryExpression b1 = Expression.MakeBinary(ExpressionType.Add,
                Expression.Constant(1), Expression.Constant(2));
            BinaryExpression b2 = Expression.MakeBinary(ExpressionType.Add,
                Expression.Constant(x), Expression.Constant(y));

            BinaryExpression b3 = Expression.MakeBinary(ExpressionType.Subtract, b1, b2);
            int result = Expression.Lambda<Func<int>>(b3).Compile()();
            Assert.AreEqual(-4, result); // 3-7
        }
    }
}
