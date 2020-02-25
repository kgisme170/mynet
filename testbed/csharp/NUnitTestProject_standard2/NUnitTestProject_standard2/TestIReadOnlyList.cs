using NUnit.Framework;
using System.Collections.Generic;
using System;
using Newtonsoft.Json;

namespace NUnitTestProject_standard2
{
    public class TestIReadOnlyList
    {
        [Test]
        public void TestNullIReadOnlyList()
        {
            IReadOnlyList<string> list = null;
            Assert.Pass();
        }
    }
}