using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;

namespace LinqRx
{
    public static class UseExtention
    {
        public static void Print<T>(this T v)
        {
            Console.WriteLine(v);
        }
    }
    public class User
    {
        public int ID
        {
            get;
            set;
        }
        public string FirstName
        {
            get;
            set;
        }
        public string LastName
        {
            get;
            set;
        }
    }

    class UseExpression
    {
        private static void PrintNames(IEnumerable<User> users)
        {
            Console.WriteLine();
            users.ForAll(user => Console.WriteLine(user.FirstName + " " + user.LastName));
        }

        private static List<User> ListUsers()
        {
            return new List<User>
            {
                new User {ID = 1, FirstName = "john", LastName = "Xingshi1"},
                new User {ID = 2, FirstName = "kevin", LastName = "Xingshi1"},
                new User {ID = 3, FirstName = "kate", LastName = "Xingshi2"}
            };
        }

        public static void Test()
        {
            Expression<Func<int, bool>> exp = i => i > 5;
            BinaryExpression binary = (BinaryExpression)exp.Body;
            Console.WriteLine(exp.Body.GetType());
            Console.WriteLine(binary.Left);
            Console.WriteLine(binary.NodeType);
            Console.WriteLine(binary.Right);

            Func<int, bool> f = i => i > 4;
            exp.Compile()(4).Print();

            Expression<Action> e = Expression.Lambda<Action>(
                Expression.Call(
                    typeof(Console).GetMethod("WriteLine", new[] { typeof(string) }),
                    Expression.Constant("hello world！")
                )
            );
            e.Compile()();

            Expression<Action> e2 = () => Console.WriteLine("hw");
            e2.Compile()();
            //
            var firstArg = Expression.Constant(2);
            var secondArg = Expression.Constant(4);
            var add = Expression.Add(firstArg, secondArg);
            add.Reduce().Print();
            //
            Expression<Func<User, bool>> FirstExp = (x => x.LastName == "Xingshi1");

            var qusers = ListUsers().AsQueryable();
            var lusers = ListUsers();
            var ausers = ListUsers().ToArray();

            // 表达式树只能放到Queryable里面使用,而且要和Where一起使用
            var result1 = qusers.Where(FirstExp);
            var result11 = qusers.Where(x => x.LastName == "Xingshi1");
            var result2 = lusers.Where(x => x.LastName == "Xingshi2");
            var result3 = ausers.Where(x => x.FirstName == "Kevin");

            Console.WriteLine();
            PrintNames(result1);
            PrintNames(result11);
            PrintNames(result2);
            PrintNames(result3);

            Console.WriteLine();
            // ms code
            ParameterExpression value = Expression.Parameter(typeof(int), "value");
            ParameterExpression result = Expression.Parameter(typeof(int), "result");

            LabelTarget label = Expression.Label(typeof(int));

            BlockExpression block = Expression.Block(
                new[] { result }, // 返回值
                Expression.Assign( // 初始化返回值
                    result,
                    Expression.Constant(1)
                ),
                Expression.Loop(
                    Expression.IfThenElse(
                        Expression.GreaterThan(value, Expression.Constant(1)), // if
                        Expression.MultiplyAssign( // when true
                            result,
                            Expression.PostDecrementAssign(value)), // result *= value--
                        Expression.Break(label, result) // when false
                    ),
                    label
                )
            );

            int factorial = Expression
                .Lambda<Func<int, int>>(block, value)
                .Compile()(5);

            Console.WriteLine(factorial); // 120
        }
    }
}
