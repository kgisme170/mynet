using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Bond;
namespace ConsoleApp2_Bond
{
    class RawData
    {
        public string Name { set; get; }
        public Int64 Id { set; get; }
        public byte[] Photo { set; get; }
    }
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
                Photo = new byte[10]
            };
            
        }
    }
}
