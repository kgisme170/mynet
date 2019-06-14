using System;

namespace ConsoleApp2
{
    //输入配置
    public class InputConfig
    {
        public string ID { get; set; }
        public string Filename { get; set; }
        public string[] ColumnNames { get; set; }
        public DateTime StartDate { get; set; }
        public int Interval { get; set; }
    }
}