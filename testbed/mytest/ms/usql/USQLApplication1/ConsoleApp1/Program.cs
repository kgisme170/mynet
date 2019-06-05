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
    class Program
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

        delegate void vFunc();
        static readonly vFunc mFunc = vf;
        public static void vf()
        {
            Console.WriteLine("func");
        }

        delegate double dFunc(int r);
        static readonly dFunc aFunc = area;
        public static double area(int r)
        {
            return 3.14 * r * r;
        }

        static void Main(string[] args)
        {
            mFunc();
            Console.WriteLine(aFunc(3));

            dFunc func = new dFunc(area);
            Console.WriteLine(func(4));
            dFunc circumference = new dFunc(delegate (int r) { return 6.28 * r; });
            Console.WriteLine(circumference(5));

            dFunc circ = r => 6.28 * r;
            Console.WriteLine(circ(8));
            Func<Double, Double> f = r => 6.28 * r;
            Console.WriteLine(f(9));

            void ai(int x) => Console.WriteLine(x + 1);
            ai(5);

            Action<int> a = ai;
            a(5);

            Action av = () => Console.WriteLine("av");
            av();
            string date = "2019-05-27 00:00:00";
            DateTime time = DateTime.Parse(date);
            Console.WriteLine(time);
            DateTime t1 = time.AddHours(23);
            string s = t1.ToString();
            Console.WriteLine(s);

            Outter o = new Outter();
            byte[] bytes = ObjectToByteArray(o);

            Outter obj = (Outter)ByteArrayToObject(bytes);
            Console.WriteLine(obj.li[1].mI); // expect "3"
        }
    }
}
