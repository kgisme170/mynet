using NUnit.Framework;
using System;

namespace NUnitTestProject_core
{
    class UseNullable
    {
        class Member
        {
            public string Name { get; set; }
        }
        class Inner
        {
            public Member MI { get; set; }
            public Member MJ { get; set; }
        }
        class Outter
        {
            public Inner Inner { get; set; }
        }
        [Test]
        public static void Test()
        {
            Outter outter = new Outter();
            Console.WriteLine(outter?.Inner?.MI?.Name);
            Console.WriteLine(outter?.Inner == null);

            var s = outter?.Inner?.MI == null ? "a" : "b";
            Assert.AreEqual(s, "a");
        }
    }
}
