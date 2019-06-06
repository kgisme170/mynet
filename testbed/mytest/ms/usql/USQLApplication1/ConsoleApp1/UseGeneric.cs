namespace ConsoleApp1
{
    class UseGeneric<UNKNOWN>
    {
        public bool Compare(UNKNOWN x, UNKNOWN y)
        {
            return x.Equals(y);
        }
    }
}
