﻿using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

namespace NUnitTestProject_core
{
    class Inner
    {
        public int Mi { get; set; }

        public string S = string.Empty;
        public string T = string.Empty;

        public Inner(int i, string t) { Mi = i; T = t; }
    }
    [Serializable]
    class Outter
    {
        [NonSerialized]
        public readonly List<Inner> li = new List<Inner>()
        {
            new Inner(2, ""),
            new Inner(3, "")
        };
    }

    class ObjectBytesConversion
    {
        public static byte[] ObjectToByteArray(object obj)
        {
            if (obj == null)
            {
                return new byte[0];
            }

            MemoryStream ms = new MemoryStream();
            new BinaryFormatter().Serialize(ms, obj);
            return ms.ToArray();
        }
        public static object ByteArrayToObject(byte[] arrBytes)
        {
            MemoryStream memStream = new MemoryStream();
            memStream.Write(arrBytes, 0, arrBytes.Length);
            memStream.Seek(0, SeekOrigin.Begin);
            return new BinaryFormatter().Deserialize(memStream);
        }
        [Test]
        public static void TestObjectBytesConversion()
        {
            Outter o = new Outter();
            byte[] bytes = ObjectToByteArray(o);

            Outter obj = (Outter)ByteArrayToObject(bytes);
            Assert.AreEqual(3, obj.li[1].Mi);
        }
    }
}
