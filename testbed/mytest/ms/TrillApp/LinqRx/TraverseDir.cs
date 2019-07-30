using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class TraverseDir
    {
        public static void Test()
        {
            DirectoryInfo[] dirs = new DirectoryInfo(@"d:\git").GetDirectories();
            var query = from d in dirs
                        where (d.Attributes & FileAttributes.System) == 0
                        select new
                        {
                            Name = d.FullName,
                            Created = d.CreationTime,
                            Files = from f in d.GetFiles()
                                    where (f.Attributes & FileAttributes.Hidden) == 0
                                    select new { FileName = f.Name, f.Length, }
                        };
            foreach(var dirFiles in query)
            {
                Console.WriteLine("Directory: " + dirFiles.Name);
                foreach(var f in dirFiles.Files)
                {
                    Console.WriteLine("" + f.FileName + ": len=" + f.Length);
                }
            }
        }
    }
}
