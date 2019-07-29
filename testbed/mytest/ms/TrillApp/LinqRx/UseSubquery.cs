using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class Student
    {
        public int Sno { get; set; } // Student number
        public int Cno { get; set; } // College number
        public int Grade { get; set; }
        public Student(int s, int c, int g)
        {
            Sno = s;
            Cno = c;
            Grade = g;
        }
        public Student(int g)
        {
            Grade = g;
        }
        public override string ToString()
        {
            return Sno.ToString() + ":" + Cno.ToString() + ":" + Grade.ToString();
        }
    }
    class Course
    {
        public int Sno { get; set; }
        public string CName { get; set; }
        public Course(int s, string c)
        {
            Sno = s;
            CName = c;
        }
    }
    class UseSubquery
    {
        public static void Test()
        {
            IEnumerable<Student> students = new[]
            {
                new Student(1,1,90),
                new Student(1,2,92),
                new Student(1,3,80),
                new Student(2,2,84),
                new Student(2,3,88),
                new Student(2,4,90),
                new Student(3,1,70),
                new Student(3,3,86),
                new Student(3,4,93)
            };

            IEnumerable<Course> courses = new[]
            {
                new Course(1, "math"),
                new Course(2, "physics"),
                new Course(2, "math"),
                new Course(3, "physics"),
            };

            var ret = students // 高于平均分的所有课程
                .Where(s => 
                       s.Grade > students
                                    .Where(ss => ss.Sno == s.Sno)
                                    .Average(ss => ss.Grade));
            ret.Print();

            var ret2 = students // 选择了math
                .Where(s =>
                       courses
                           .Where(c => c.Sno == s.Sno)
                           .Any(c => c.CName == "math"))
                .Select(s => s.Sno)
                .Distinct();
            ret2.Print();
        }
    }
}
