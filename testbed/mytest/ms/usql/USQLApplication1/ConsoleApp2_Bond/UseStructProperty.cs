namespace ConsoleApp2_Bond
{

    public struct Size
    {
        public int Length;// { get; set; }
        public int Width { get; set; }
    }

    public struct Room
    {
        public Size TableSize { get; set; }
        public Size TvSize { get; set; }
    }
    class UseStructProperty
    {
        static void Test(string[] args)
        {
            Room r = new Room()
            {
                TableSize = new Size() { Length = 100, Width = 80 },
                TvSize = new Size() { Length = 10, Width = 8 }
            };

            //r.TableSize.Length = 0; 会有编译错误
            UseMyData.Test();
        }
    }
}
