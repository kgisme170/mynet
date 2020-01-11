namespace ConsoleApp1
{
    class Immutable // immutable object
    {
        public Immutable(string _cur, string _addr)
        {
            Currency = _cur;
            Address = _addr;
        }

        public string Currency { get; }
        public string Address { get; }
    }

    class ImmutableTest
    {
        public static void Test()
        {
            var m = new Immutable("usd", "us");
            string s = m.Currency;
        }
    }
}
