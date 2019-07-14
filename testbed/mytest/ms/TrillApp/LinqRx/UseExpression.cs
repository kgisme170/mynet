using System;
using System.Linq.Expressions;

namespace LinqRx
{
    class UseExpression
    {
        public static void Test()
        {
            Expression<Func<int, bool>> exp = i => i > 5;
            BinaryExpression binary = (BinaryExpression)exp.Body;
            Console.WriteLine(exp.Body.GetType());
            Console.WriteLine(binary.Left);
            Console.WriteLine(binary.NodeType);
            Console.WriteLine(binary.Right);
        }
    }
}
