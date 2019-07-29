using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class UseGroup
    {
        class Student
        {
            public int Year { get; set; }
            public string FirstName { get; set; }
            public string LastName { get; set; }
            public Student(int y, string f, string n)
            {
                Year = y;
                FirstName = f;
                LastName = n;
            }
            public override string ToString()
            {
                return "Year=" + Year + ", FirstName=" + FirstName + ", LastName=" + LastName;
            }
            public static Student NextYear(Student s)
            {
                return new Student(s.Year + 1, s.FirstName, s.LastName);
            }
        }
        public static void Test()
        {
            var students = new[]
            {
                new Student(1, "John", "Smith"),
                new Student(1, "Dave", "Smith"),
                new Student(2, "David", "Smith"),
                new Student(2, "Kate", "Smith"),
                new Student(2, "Faren", "High"),
                new Student(2, "Hather", "High"),
                new Student(3, "Group", "Love"),
                new Student(3, "John", "Love"),
            };

            var nextStudents = students.Select(s => Student.NextYear(s));
            nextStudents.Print();

            var ret1 = from s in 
                           students
                       group s by s.Year
                       into ng
                       from g in
                           (from s in ng
                            group s by s.LastName)
                       group g by ng.Key into gg
                       select gg;
            var ret2 = from g in ret1
                       from s in g
                       from r in s
                       select r;

            foreach(var g in ret1)
            {
                Console.WriteLine("(1)--------------------Year");
                foreach (var e in g)
                {
                    Console.WriteLine("(2)--------------------Smith");
                    foreach (var i in e)
                    {
                        Console.WriteLine("(3)--------------------");
                        Console.WriteLine(i);
                    }
                }
            }
            Console.WriteLine("---------------------------");
            ret2.Print();
        }
    }
}
