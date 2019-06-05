using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    [Serializable]
    class Inner
    {
        public readonly int mI;
        public readonly string s = "abc";
        readonly List<string> ls = new List<string>()
        {
            "abc",
            "xyz"
        };
        public Inner(int i) { mI = i; }
    }
    [Serializable]
    class Outter
    {
        readonly Inner obj = new Inner(1);
        public readonly List<Inner> li = new List<Inner>()
        {
            new Inner(2),
            new Inner(3)
        };
    }

    class ObjectBytesConversion
    {
        public static byte[] ObjectToByteArray(Object obj)
        {
            if (obj == null)
                return null;
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream ms = new MemoryStream();
            bf.Serialize(ms, obj);
            return ms.ToArray();
        }
        public static Object ByteArrayToObject(byte[] arrBytes)
        {
            MemoryStream memStream = new MemoryStream();
            BinaryFormatter binForm = new BinaryFormatter();
            memStream.Write(arrBytes, 0, arrBytes.Length);
            memStream.Seek(0, SeekOrigin.Begin);
            Object obj = (Object)binForm.Deserialize(memStream);
            return obj;
        }

        public static void test()
        {
            Outter o = new Outter();
            byte[] bytes = ObjectToByteArray(o);

            Outter obj = (Outter)ByteArrayToObject(bytes);
            Console.WriteLine(obj.li[1].mI); // expect "3"
        }
    }
}
