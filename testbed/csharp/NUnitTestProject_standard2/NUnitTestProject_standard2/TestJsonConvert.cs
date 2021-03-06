﻿using NUnit.Framework;
using System;
using Newtonsoft.Json;

namespace UseNewtonSoftJson
{
    public class MyClass
    {
        public int MyNum { get; set; }

        public string MyStr { get; set; } = string.Empty;
    }

    public static class TestJsonConvert
    {
        [Test]
        public static void Convert(string[] args)
        {
            Console.WriteLine("Hello World!");
            MyClass myClass = new MyClass { MyNum = 10, MyStr = "Hello World" };

            var s = JsonConvert.SerializeObject(myClass);
            Console.WriteLine(s);

            var info = JsonConvert.DeserializeObject<MyClass>(s);
            Console.WriteLine(info);
        }
    }
}