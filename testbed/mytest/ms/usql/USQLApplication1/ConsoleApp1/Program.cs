﻿using System;
using System.Threading;

namespace ConsoleApp1
{
    class Program
    {
        public static volatile int count = 0;
        public static void Method()
        {
            Console.WriteLine("s");
            while (count < 1000)
            {
                Thread.Sleep(10);
                ++count;
            }
            Console.WriteLine("e");
        }

        public static void Main(String [] args)
        {
            new Thread(Method).Start();
            Thread.Sleep(4000);
            count = 1000;
            Console.ReadLine();
        }
    }
}
