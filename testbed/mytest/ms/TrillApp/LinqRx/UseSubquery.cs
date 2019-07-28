using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class Student
    {
        public int Sno { get; set; }
        public int Cno { get; set; }
        public int Grade { get; set; }
        public Student(int s, int c, int g)
        {
            Sno = s;
            Cno = c;
            Grade = g;
        }
        public override string ToString()
        {
            return Sno.ToString() + ":" + Cno.ToString() + ":" + Grade.ToString();
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

            var ret = students
                .Where(s => 
                       s.Grade > students
                                    .Where(ss => ss.Sno == s.Sno)
                                    .Average(ss => ss.Grade));
            ret.Print();
        }
    }
}
