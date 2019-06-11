using Microsoft.Bond;
using System;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

namespace ConsoleApp2_Bond
{
    class UseMyData
    {
        public static void Test()
        {
            MyData myData = new MyData
            {
                Name = "john",
                Id = 123,
                Photo = new BondBlob(new byte[10])
            };

            RawData rawData = new RawData
            {
                Name = "john",
                Id = 123,
                Photo = new byte[100]
            };

            BinaryFormatter bitFormatter = new BinaryFormatter();
            MemoryStream memoryStream = new MemoryStream();
            bitFormatter.Serialize(memoryStream, rawData);
            byte [] bytes = memoryStream.ToArray();
            Console.WriteLine(bytes.Length);
        }
    }
}
