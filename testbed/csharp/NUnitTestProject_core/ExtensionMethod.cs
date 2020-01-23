using NUnit.Framework;

namespace NUnitTestProject_core
{
    public class My
    {
        public int F() { return 3; }
        public int MI { get; set; }
    }
    public static class Ex
    {
        public static int G(this My obj, int i)
        {
            return obj.MI + obj.F() + i;
        }
    }
    class ExtensionMethod
    {
        [Test]
        public static void Test()
        {
            My m = new My();
            Assert.AreEqual(6, m.G(3));
        }
    }
}
