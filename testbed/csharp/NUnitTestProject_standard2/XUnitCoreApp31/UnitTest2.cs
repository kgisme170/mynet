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

        public class Foo : IFoo
        {
            private readonly IFoo _foo;

            public Foo(IFoo foo)
            {
                _foo = foo;
            }

            public string Name { get; set; } = string.Empty;
            public Bar Bar { get => throw new NotImplementedException(); set => throw new NotImplementedException(); }
            public int Value { get => throw new NotImplementedException(); set => throw new NotImplementedException(); }

            public bool Add(int value)
            {
                throw new NotImplementedException();
            }

            public bool DoSomething(string value)
            {
                throw new NotImplementedException();
            }

            public bool DoSomething(int number, string value)
            {
                throw new NotImplementedException();
            }

            public string DoSomethingStringy(string value)
            {
                throw new NotImplementedException();
            }

            public int GetCount()
            {
                throw new NotImplementedException();
            }

            public string GetName()
            {
                return _foo.Name;
            }

            public void SetName(string name)
            {
                _foo.Name = name;
            }

            public bool Submit(ref Bar bar)
            {
                throw new NotImplementedException();
            }

            public bool TryParse(string value, out string outputValue)
            {
                throw new NotImplementedException();
            }
        }

        [Fact]
        public static void UseMock()
        {
            var mock = new Mock<IFoo>();

            mock.Setup(foo => foo.DoSomething("ping")).Returns(true);
            mock.Setup(foo => foo.DoSomething("reset")).Throws<InvalidOperationException>();
            mock.Setup(foo => foo.DoSomething("")).Throws(new ArgumentException("command"));

            var outString = "ack";
            mock.Setup(foo => foo.TryParse("ping", out outString)).Returns(true);

            var instance = new Bar();
            mock.Setup(foo => foo.Submit(ref instance)).Returns(true);
            mock.Setup(x => x.DoSomethingStringy(It.IsAny<string>())).Returns((string s) => s.ToLower());
            mock.Setup(foo => foo.GetCount()).Returns(() => 1);
            mock.Setup(foo => foo.Name).Returns("bar");
           
            //mock.SetupGet(x => x.Name).Returns("new Foo Name");
            var fooName = new Foo(mock.Object).GetName();
            mock.VerifyGet(m => m.Name, Times.Once);
            new Foo(mock.Object).SetName("new Foo Name");//new

            //mock.SetupSet(foo => foo.Name = "new Foo Name");//remove
            //mock.SetupProperty(foo => foo.Name, "new Foo Name");
            mock.VerifySet(foo => foo.Name = "new Foo Name"); // TODO

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
