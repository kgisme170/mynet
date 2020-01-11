using System;
using System.Linq;
using System.Xml.Linq;
using System.Collections.Generic;
namespace DataApp1
{
    class UseXml
    {
        public static void Test()
        {
            var xml = XDocument.Load(@"XmlToText.xml");
            IEnumerable<XElement> nodes =
                from item in xml.Descendants("floor")
                select item;
            foreach(var n in nodes)
            {
                Console.Write(n.Element("name").Value+":");
                Console.WriteLine(n.Element("rooms").Value);
            }
        }
    }
}
