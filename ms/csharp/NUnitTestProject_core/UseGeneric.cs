namespace NUnitTestProject_core
{
    class UseGeneric<UNKNOWN>
    {
        public bool Compare(UNKNOWN x, UNKNOWN y)
        {
            return x.Equals(y);
        }
    }
}
