using System;

namespace ConsoleApp2
{
    partial class Program
    {
        public static int GetChoice()
        {
            int choice = -1;
            while(choice != -1)
            {
                try
                {
                    Console.WriteLine("Enter choice, 0 to quit");
                    Console.WriteLine("1: Test generated timed data");
                    Console.WriteLine("2: Test from excel file data");

                    string line = Console.ReadLine();
                    choice = int.Parse(line);
                }
                catch (Exception)
                {

                }
            }
            return choice;
        }

        static void Main(string[] args)
        {
            int choice = GetChoice();
            switch (choice)
            {
                case 0:
                    Console.WriteLine("Quit");
                    break;
                case 1:
                    Test1();
                    break;
                case 2:
                    Test2();
                    break;
                default:
                    Console.WriteLine("error");
                    break;
            }
        }
    }
}
