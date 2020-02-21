using System;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Xml.Linq;
using System.Collections.Generic;
using NUnit.Framework;

namespace NUnitTestProject_core
{
    class UseXml
    {
        public static DirectoryInfo GetExecutingDirectory()
        {
            var location = new Uri(Assembly.GetEntryAssembly().GetName().CodeBase);
            return new FileInfo(location.AbsolutePath).Directory;
        }
        [Test]
        public static void TestUseXml()
        {
            var exePath = GetExecutingDirectory();
            var di = new DirectoryInfo(exePath.FullName);
            var xml = XDocument.Load(Path.Combine(di.Parent.Parent.Parent.FullName, @"XmlToText.xml"));
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
