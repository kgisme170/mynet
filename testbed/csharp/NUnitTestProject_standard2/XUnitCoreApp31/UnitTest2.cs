using Moq;
using System;
using Xunit;

namespace XUnitCoreApp31
{
    public class UseMoq
    {
        public interface IFoo
        {
            string Name { get; set; }
            Bar Bar { get; set; }
            int Value { get; set; }
            bool DoSomething(string value);
            bool DoSomething(int number, string value);
            string DoSomethingStringy(string value);
            bool TryParse(string value, out string outputValue);
            bool Submit(ref Bar bar);
            int GetCount();
            bool Add(int value);
        }

        public class Bar
        {
            public virtual bool Submit() { return false; }
        }

        [Fact]
        public static void UseMock()
        {
            var mock = new Mock<IFoo>();
            /*
            mock.Setup(foo => foo.DoSomething("ping")).Returns(true);
            mock.Setup(foo => foo.DoSomething("reset")).Throws<InvalidOperationException>();
            mock.Setup(foo => foo.DoSomething("")).Throws(new ArgumentException("command"));

            var outString = "ack";
            mock.Setup(foo => foo.TryParse("ping", out outString)).Returns(true);

            var instance = new Bar();
            mock.Setup(foo => foo.Submit(ref instance)).Returns(true);
            mock.Setup(x => x.DoSomethingStringy(It.IsAny<string>())).Returns((string s) => s.ToLower());
            mock.Setup(foo => foo.GetCount()).Returns(() => 1);
            */
            // mock.Setup(foo => foo.Name).Returns("bar");
            mock.SetupSet(foo => foo.Name = "foo");
            mock.VerifySet(foo => foo.Name = "foo");

            mock.SetupProperty(f => f.Name);
            mock.SetupProperty(f => f.Name, "foo");
            IFoo foo = mock.Object;
            Assert.Equal("foo", foo.Name);
            foo.Name = "bar";
            Assert.Equal("bar", foo.Name);

            mock.SetupProperty(f => f.Name);
            mock.SetupProperty(f => f.Name, "foo");
            IFoo foo1 = mock.Object;
            Assert.Equal("foo", foo1.Name);
            foo1.Name = "bar";
            Assert.Equal("bar", foo1.Name);
        }
    }
}
