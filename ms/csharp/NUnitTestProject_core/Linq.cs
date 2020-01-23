using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NUnitTestProject_core
{
    class Linq
    {
        [Test]
        public static void CrossJoin()
        {
            string[] players = { "Tom", "Jay", "Mary" };
            IEnumerable<string> query = from name1 in players
                                        from name2 in players
                                        where name1 != name2
                                        select name1 +" vs " + name2;
            Assert.AreEqual(query.Count(), 6);
        }

        public static int Multi10(int n)
        {
            Console.WriteLine("multi10");
            return n * 10;
        }
        [Test]
        public static void DeferExecution()
        {
            var numbers = Enumerable.Range(2, 5).ToList();
            Assert.AreEqual(5, numbers.Count());

            var q1 = numbers.Select(Multi10);
            numbers.AddRange(Enumerable.Range(12, 5));
            var l1 = q1.ToList(); // executes query
            int m = 10;
            var q = new int[] { 1, 2 }.Select(n => n * m);
            m = 20;
            Assert.AreEqual(q.ToList()[1], 40);
        }

        public static void TraverseDir(string root)
        {
            DirectoryInfo[] dirs = new DirectoryInfo(root).GetDirectories();
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
            foreach (var dirFiles in query)
            {
                Console.WriteLine("Directory: " + dirFiles.Name);
                foreach (var f in dirFiles.Files)
                {
                    Console.WriteLine("" + f.FileName + ": len=" + f.Length);
                }
            }
        }

        [Test]
        public static void Count()
        {
            var v = new[]
{
                new {Id = 1, Name = "abc" },
                new {Id = 2, Name = "xyz" },
                new {Id = 3, Name = "123" },
                new {Id = 4, Name = "456" },
                new {Id = 5, Name = "mod" },
                new {Id = 6, Name = "uio" },
                new {Id = 7, Name = "abc" },
                new {Id = 1, Name = "abc" }
            };
            var c = v.Count();
            Assert.AreEqual(c, v.Length);

            var l = from e in v
                    group e by new { e.Id, e.Name } into eg
                    select new { k = eg.Key, CustCount = eg.Count() };
            var d = from e in l
                    where e.CustCount > 1
                    select e;
            Assert.AreEqual(d.Count(), 1);
        }
        /*
        public static void UseMethod()
        {
            int[] foo =
                (from n in Enumerable.Range(0, 100)
                 select n * n).ToArray();
            foo
                .Where(n => n > 50 && n < 300)
                .Select(n => "n=" + n)
                .Take(5)
                .Skip(1)
                .ForAll(Console.WriteLine);
        }*/ // TODO
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
        class Teacher
        {
            public string Name { get; set; }
            public List<Student> Students;

            public Teacher(string n, List<Student> students)
            {
                Name = n;
                Students = students;
            }
        }
        [Test]
        public static void SelectMany()
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
            Assert.AreEqual(t1.Count(), 4);

            var t2 = from t in teachers
                     select new
                     {
                         t.Name,
                         students = from s in t.Students
                                    where s.Grade > 80
                                    select s // left outer join
                     };
            Assert.AreEqual(t2.SelectMany(t => t.students).Count(), 12);

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
            Assert.AreEqual(t3.SelectMany(t => t.students).Count(), 12);

            string[] fullNames = { "Anne Williams", "John Fred Smith", "Sue Green" };
            IEnumerable<string> query = fullNames.SelectMany(name => name.Split());
            Assert.AreEqual(query.Count(), 7);
        }

        class Person
        {
            public string FirstName { get; set; }
            public string LastName { get; set; }
        }

        class Pet
        {
            public string Name { get; set; }
            public Person Owner { get; set; }
        }
        public static void XElement()
        {
            string[] fruits = { "apple", "banana", "mango", "orange", "passionfruit", "grape" };

            var f = fruits.Select((fruit, index) =>
                          new { index, str = fruit.Substring(0, index) });

            Person magnus = new Person { FirstName = "Magnus", LastName = "Hedlund" };
            Person terry = new Person { FirstName = "Terry", LastName = "Adams" };
            Person charlotte = new Person { FirstName = "Charlotte", LastName = "Weiss" };
            Person arlene = new Person { FirstName = "Arlene", LastName = "Huff" };

            var people = new List<Person>() {
                magnus, terry, charlotte, arlene
            };

            var pets = new List<Pet>() {
                new Pet { Name = "Barley", Owner = terry },
                new Pet { Name = "Boots", Owner = terry },
                new Pet { Name = "Whiskers", Owner = charlotte },
                new Pet { Name = "Blue Moon", Owner = terry },
                new Pet { Name = "Daisy", Owner = magnus }
            };

            XElement ownersAndPets = new XElement("PetOwners",
                from person in people
                join pet in pets on person equals pet.Owner into gj
                select new XElement("Person",
                                    new XAttribute("FirstName", person.FirstName),
                                    new XAttribute("LastName", person.LastName),
                                    from subpet in gj
                                    select new XElement("Pet", subpet.Name))
            );
            Console.WriteLine(ownersAndPets);
        }

        class Employee
        {
            public string EmpName { get; set; }
            public int DeptId { get; set; }
        }
        class Department
        {
            public int DeptId { get; set; }
            public string DeptName { get; set; }
        }
        public static void Join()
        {
            IEnumerable<Employee> employees = new[]
            {
                new Employee{ EmpName = "John", DeptId = 1 },
                new Employee{ EmpName = "Kate", DeptId = 2 },
                new Employee{ EmpName = "Dave", DeptId = 1 },
                new Employee{ EmpName = "Dane", DeptId = 3 },
                new Employee{ EmpName = "Greg", DeptId = 4 },
                new Employee{ EmpName = "Tony", DeptId = 4 },
            };

            IEnumerable<Department> departments = new[]
            {
                new Department{ DeptId = 1, DeptName = "math" },
                new Department{ DeptId = 3, DeptName = "physics" },
                new Department{ DeptId = 4, DeptName = "history" },
                new Department{ DeptId = 5, DeptName = "mandarine" },
            };

            var result = from e in employees
                         join d in departments
                         on e.DeptId equals d.DeptId
                         select new { e.EmpName, d.DeptName };

            var ret2 = employees.Join(
                departments,
                left => left.DeptId,
                right => right.DeptId,
                (left, right) => new { left.EmpName, right.DeptName });

            var ret3 = from e in employees
                       where !(from d in departments select d.DeptId).Contains(e.DeptId)
                       select e.EmpName;

            var ret4 = from e in employees select e.EmpName;
            var ret5 = employees.Select(e => e.EmpName);

            var ret6 = employees
                .Where(e => !departments.Select(d => d.DeptId).Contains(e.DeptId))
                .Select(e => e.EmpName);

            var ret7 = from e in employees
                       join d in departments
                       on e.DeptId equals d.DeptId into gj
                       from subdept in gj.DefaultIfEmpty() // left outer join
                       select new { e.EmpName, subdept?.DeptName };

            var ret8 = from e in employees
                       join d in departments on e.DeptId equals d.DeptId into gj
                       from subdept in gj.DefaultIfEmpty()
                       where subdept is null
                       select new { e.EmpName, subdept?.DeptName };
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
        [Test]
        public static void Subquery()
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
            };//TODO

            var ret = students // 高于平均分的所有课程
                .Where(s =>
                       s.Grade > students
                                    .Where(ss => ss.Sno == s.Sno)
                                    .Average(ss => ss.Grade));

            var ret2 = students // 选择了math
                .Where(s =>
                       courses
                           .Where(c => c.Sno == s.Sno)
                           .Any(c => c.CName == "math"))
                .Select(s => s.Sno)
                .Distinct();

            int[] ids = { 2, 3, 4 };
            var avg = from s in students
                      let i = s.Sno
                      where ids.Contains(i)
                      group s by s.Sno into sg
                      select new
                      {
                          Sno = sg.Key,
                          Avg = sg.Average(s => s.Grade),
                          Max = sg.Max(s => s.Grade),
                          Min = sg.Min(s => s.Grade)
                      };

        }
        class Pupil
        {
            public int Year { get; set; }
            public string FirstName { get; set; }
            public string LastName { get; set; }
            public Pupil(int y, string f, string n)
            {
                Year = y;
                FirstName = f;
                LastName = n;
            }
            public override string ToString()
            {
                return "Year=" + Year + ", FirstName=" + FirstName + ", LastName=" + LastName;
            }
            public static Pupil NextYear(Pupil s)
            {
                return new Pupil(s.Year + 1, s.FirstName, s.LastName);
            }
        }
        [Test]//TODO
        public static void Group()
        {
            var students = new[]
            {
                new Pupil(1, "John", "Smith"),
                new Pupil(1, "Dave", "Smith"),
                new Pupil(2, "David", "Smith"),
                new Pupil(2, "Kate", "Smith"),
                new Pupil(2, "Faren", "High"),
                new Pupil(2, "Hather", "High"),
                new Pupil(3, "Group", "Love"),
                new Pupil(3, "John", "Love"),
            };

            var nextStudents = students.Select(s => Pupil.NextYear(s));
            Console.WriteLine("=================================");
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
            var ret3 = from s in students
                       group new { s.FirstName, s.LastName }
                       by s.Year into sg
                       where sg.Key > 1
                       from g in sg
                       select g;
            Console.WriteLine("=================================");
            foreach (var g in ret1)
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
        }
    }
}
