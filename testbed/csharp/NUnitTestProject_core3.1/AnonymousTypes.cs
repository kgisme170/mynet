using NUnit.Framework;
using System;

namespace NUnitTestProject_core
{
    class AnonymousTypes
    {
        public static void Split(string s, out string name, out string address, out string postcode)
        {
            string[] array = s.Split(',');
            name = array[0];
            address = array[1];
            postcode = array[2];
        }

        public static Tuple<string, string, string> Parse(string s)
        {
            string[] array = s.Split(',');
            return new Tuple<string, string, string>(array[0], array[1], array[2]);
        }

        public static object ParseData(string s)
        {
            string[] array = s.Split(',');
            return new { Name = array[0], Address = array[1], PostCode = array[2] };
        }

        static T Cast<T>(object obj, T _)
        {
            return (T)obj;
        }

        [Test]
        public static void TestAnonymousTypes()
        {
            string s = "john,20st,100020";

            Split(s, out string name, out string address, out string postcode);
            Console.WriteLine(name + address + postcode);

            Tuple<string, string, string> t = Parse(s);
            Console.WriteLine(t.Item1);

            var o = Cast(ParseData(s), new { Name = "AnonymousTypes", Address = "", PostCode = "" });
            Assert.True(o.Name.Equals("john"));
        }
    }
}
