using System;
using System.Diagnostics;
using System.IO;
using Xunit;

namespace XUnitTestProject1
{
    public class UnitTest1
    {
        static void print(string s)
        {
            Debug.WriteLine(s);
        }
        static string GetParent(string path, int level)
        {
            if (level <= 1)
            {
                throw new Exception("Level should be >=1");
            }
            var ret = path;
            for(int i = 0; i < level; ++i)
            {
                ret = Directory.GetParent(ret).FullName;
            }
            return ret;
        }
        [Fact]
        public void Test1()
        {
            try
            {
                // Get the current directory.
                string path = Directory.GetCurrentDirectory();
                print(path);
                var p = Path.Join(GetParent(path, 5), "cert");
                print(p);
                if (!Directory.Exists(p))
                {
                    Directory.CreateDirectory(p);
                }
                Console.WriteLine("The current directory is {0}", path);

                // Change the current directory.
                Environment.CurrentDirectory = (p);
                if (path.Equals(Directory.GetCurrentDirectory()))
                {
                    Console.WriteLine("You are in the temp directory.");
                }/*
                else
                {
                    Console.WriteLine("You are not in the temp directory.");
                }*/
                //CertInfo.main("client.key");
                Console.WriteLine("------------------");
                CertInfo.Run("client.pem");
                Console.WriteLine("------------------");
                //CertInfo.main("server.key");
                Console.WriteLine("------------------");
                CertInfo.Run("server.pem");
                Console.WriteLine("------------------");

            }
            catch (Exception e)
            {
                print("The process failed: " + e.ToString());
            }
        }
    }
}
