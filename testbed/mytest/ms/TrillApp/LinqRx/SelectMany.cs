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

        public Teacher(string n, List<Student> students)
        {
            this.Name = n;

            this.Students = students;
        }
    }
    class SelectMany // 展开成1维
    {
        public static void Test()
        {
            List<Teacher> teachers = new List<Teacher>
            {
                new Teacher("abey",  new List<Student>{ new Student(100),new Student(90),new Student(30) }),
                new Teacher("betty", new List<Student>{ new Student(100),new Student(90),new Student(60) }),
                new Teacher("carol", new List<Student>{ new Student(100),new Student(90),new Student(40) }),
                new Teacher("dave",  new List<Student>{ new Student(100),new Student(90),new Student(60) }),
                new Teacher("eric",  new List<Student>{ new Student(100),new Student(90),new Student(50) }),
                new Teacher("frank", new List<Student>{ new Student(100),new Student(90),new Student(60) }),
                new Teacher("gorden",new List<Student>{ new Student(40),new Student(70),new Student(60) })
            };
            var t1 = teachers
                .SelectMany(t => t.Students)
                .Where(s => s.Grade < 60);
            t1.Print();

            var t2 = from t in teachers
                     select new
                     {
                         t.Name,
                         students = from s in t.Students
                                    where s.Grade > 80
                                    select s // left outer join
                     };
            foreach(var t in t2)
            {
                Console.WriteLine(t.Name);
                t.students.Print();
            }
            Console.WriteLine("============================================");
            var t3 = from t in teachers
                     let highScores = from s in t.Students
                                      where s.Grade > 80
                                      select s
                     where highScores.Any() // inner join
                     select new
                     {
                         t.Name,
                         students = highScores
                     };
            foreach (var t in t3)
            {
                Console.WriteLine(t.Name);
                t.students.Print();
            }

            string[] fullNames = { "Anne Williams", "John Fred Smith", "Sue Green" };
            IEnumerable<string> query = fullNames.SelectMany(name => name.Split());
            query.Print();

            var q2 = from name in fullNames
                     from n in name.Split()
                     select n + " is from [" + name + "]";
            q2.Print();
        }
    }
}
