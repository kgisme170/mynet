namespace UseNetCore31_task
{
    public interface Imy
    {
        int X { get; set; }
    }

    public class MyImpl : Imy
    {
        private int _x;
        int Imy.X
        {
            get => _x;
            set => _x = value;
        }
    }

    class ExplicitInterface
    {
        public void TestExplicitInterface()
        {
            var o = new MyImpl();
            ((Imy)o).X = 3;
        }
    }
}
