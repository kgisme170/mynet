using Moq;
using Xunit;

namespace XUnitCoreApp31
{
    public class UseMockVerify
    {
        public interface IFoo
        {
            string Name { get; set; }
        }

        public class Foo : IFoo
        {
            private readonly IFoo _foo;

            public Foo(IFoo foo)
            {
                _foo = foo;
            }

            public string Name { get; set; } = string.Empty;

            public string GetName()
            {
                return _foo.Name;
            }

            public void SetName(string name)
            {
                _foo.Name = name;
            }
        }

        [Fact]
        public static void UseMock()
        {
            var mock = new Mock<IFoo>();
            mock.SetupGet(x => x.Name).Returns("foo");

            var fooName = new Foo(mock.Object).GetName();
            mock.VerifyGet(m => m.Name, Times.Once);

            new Foo(mock.Object).SetName("new Foo Name");
            mock.VerifySet(m => m.Name = "new Foo Name", Times.Once);
        }
    }
}
