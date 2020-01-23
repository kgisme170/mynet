using System;
using System.Collections.Generic;
using System.Text;

namespace NUnitTestProject_core
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
        }
    }
}
