using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

namespace NUnitTestProject_core
{
    [Serializable]
    class Inner
    {
        public readonly int mI;
        public readonly string s = "abc";
        public Inner(int i) { mI = i; }
    }
    [Serializable]
    class Outter
    {
        public readonly List<Inner> li = new List<Inner>()
        {
            new Inner(2),
            new Inner(3)
        };
    }

    class ObjectBytesConversion
    {
        public static byte[] ObjectToByteArray(object obj)
        {
            if (obj == null)
            {
                return null;
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
        public static void Test()
        {
            Outter o = new Outter();
            byte[] bytes = ObjectToByteArray(o);

            Outter obj = (Outter)ByteArrayToObject(bytes);
            Assert.AreEqual(3, obj.li[1].mI);
        }
    }
}
