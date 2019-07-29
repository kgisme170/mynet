using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
{
    class Teacher
    {
        public string Name { get; set; }

        public List<Student> Students;

        public Teacher(string order, List<Student> students)
        {
            this.Name = order;

            this.Students = students;
        }
    }
    class SelectMany // 展开成1维
    {
        public static void Test()
        {
            List<Teacher> teachers = new List<Teacher>
            {
                new Teacher("a",new List<Student>{ new Student(100),new Student(90),new Student(30) }),
                new Teacher("b",new List<Student>{ new Student(100),new Student(90),new Student(60) }),
                new Teacher("c",new List<Student>{ new Student(100),new Student(90),new Student(40) }),
                new Teacher("d",new List<Student>{ new Student(100),new Student(90),new Student(60) }),
                new Teacher("e",new List<Student>{ new Student(100),new Student(90),new Student(50) }),
                new Teacher("f",new List<Student>{ new Student(100),new Student(90),new Student(60) }),
                new Teacher("g",new List<Student>{ new Student(40),new Student(90),new Student(60) })
            };
            var t1 = teachers.SelectMany(t => t.Students).Where(s => s.Grade < 60);
            t1.Print();
        }
    }
}
