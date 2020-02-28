namespace NUnitTestProject_standard2
{
    public partial class Tests
    {
        public class Bar<T>
        where T : Foo, new()
        {
            public T Create()
            {
                return new T();
            }
        }
    }
}