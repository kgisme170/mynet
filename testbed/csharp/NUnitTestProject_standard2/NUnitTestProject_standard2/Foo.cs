using System;

namespace NUnitTestProject_standard2
{
    public partial class Tests
    {
        public class Foo
        {
            public Foo()
            {
                _i = 0;
            }

            public Foo(int i)
            {
                _i = i;
            }

            public void print()
            {
                Console.WriteLine(_i);
            }

            private readonly int _i;
        }
    }
}