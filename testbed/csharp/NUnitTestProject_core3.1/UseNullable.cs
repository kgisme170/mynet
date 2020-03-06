using NUnit.Framework;
using System;

namespace NUnitTestProject_core
{
    class UseNullable
    {
        class Member
        {
            public string Name { get; set; } = string.Empty;
        }
        class Inner
        {
            public Member MI { get; set; } = new Member();
            public Member MJ { get; set; } = new Member();
        }
        class Outter
        {
            public Inner Inner { get; set; } = new Inner();
        }
        [Test]
        public static void TestUseNullable()
        {
            Outter outter = new Outter();
            Console.WriteLine(outter?.Inner?.MI?.Name);
            Console.WriteLine(outter?.Inner == null);

            var s = outter?.Inner?.MI == null ? "a" : "b";
            Assert.AreEqual(s, "a");
        }
    }
}
