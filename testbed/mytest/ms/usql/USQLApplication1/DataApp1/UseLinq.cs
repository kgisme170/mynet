using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataApp1
{
    enum Gender
    {
        M,
        F
    }
    class Employee
    {
        public int Employee_id { get; set; }
        public string First_name { get; set; }
        public string Last_name { get; set; }
        public Gender gender { get; set; }
        public int Department_id { get; set; }
        public int Salary { get; set; }

        public override string ToString()
        {
            var sb = new StringBuilder();
            sb.Append("Employee_id=" + Employee_id);
            sb.Append(",First_name=" + First_name);
            sb.Append(",Last_name=" + Last_name);
            sb.Append(",gender=" + gender);
            sb.Append(",Department_id=" + Department_id);
            sb.Append(",Salary=" + Salary);
            return sb.ToString();
        }
    }
    class Department
    {
        public int Department_id { get; set; }
        public string Department_name { get; set; }
    }
    class UseLinq
    {
        public static void Test()
        {
            IEnumerable<Employee> ie = new List<Employee>()
            {
                new Employee
                {
                    Employee_id=2002,
                    First_name="Super",
                    Last_name="Man",
                    gender=Gender.M,
                    Department_id=1,
                    Salary=75000
                },
                new Employee
                {
                    Employee_id=2003,
                    First_name="Jessica",
                    Last_name="Liyers",
                    gender=Gender.F,
                    Department_id=1,
                    Salary=60000
                },
                new Employee
                {
                    Employee_id=2004,
                    First_name="Bonnie",
                    Last_name="Adams",
                    gender=Gender.M,
                    Department_id=1,
                    Salary=55000
                },
                new Employee
                {
                    Employee_id=2005,
                    First_name="James",
                    Last_name="Bakon",
                    gender=Gender.M,
                    Department_id=2,
                    Salary=65000
                },
                new Employee
                {
                    Employee_id=2006,
                    First_name="Michael",
                    Last_name="Robins",
                    gender=Gender.M,
                    Department_id=2,
                    Salary=60000
                },
                new Employee
                {
                    Employee_id=2007,
                    First_name="Stacy",
                    Last_name="Jacobs",
                    gender=Gender.F,
                    Department_id=2,
                    Salary=59000
                },
            };

            IEnumerable<Department> id = new List<Department>()
            {
                new Department()
                {
                    Department_id = 1,
                    Department_name = "IT"
                },
                new Department()
                {
                    Department_id = 2,
                    Department_name = "Sales"
                } 
            };

            var employ_between_2003_2006 = ie.Where(x => x.Employee_id >= 2003 && x.Employee_id <=2006);
            foreach(var em in employ_between_2003_2006)
            {
                Console.WriteLine(em);
            }

            var highest_salary = ie.Max(x => x.Salary);
            Console.WriteLine(highest_salary);

            var max2nd = ie.OrderByDescending(x => x.Salary).Skip(1).First();
            Console.WriteLine(max2nd);

            var maxOther = ie.Where(x => x.Salary != highest_salary).Max(x => x.Salary);
            Console.WriteLine(maxOther);

            /* Using extented methods
            var combine1 = ie.Join(id, e => e.Department_id, d => d.Department_id,
                (e, d) => new
                {
                    Dep = d.Department_name,
                    Sal = e.Salary
                });*/
            Console.WriteLine("-----------");
            var combine1 = from e in ie
                           join d in id
                           on e.Department_id equals d.Department_id
                           where e.Salary == highest_salary
                           select new
                           {
                               Dep = d.Department_name,
                               Sal = e.Salary
                           };
            foreach(var c in combine1)
            {
                Console.WriteLine(c.Dep+","+c.Sal);
            }

            Console.WriteLine("-----------");
            var group1 = from e in ie
                         group e by e.Department_id into deptgrp
                         let totsal = deptgrp.Max(x => x.Salary)
                         select new
                         {
                             Dept = deptgrp.Key,
                             TopSal_id = deptgrp.First(s => s.Salary == totsal).Employee_id,
                             Salary = totsal
                         };
            foreach(var g in group1)
            {
                Console.WriteLine(g.Dept + "," + g.TopSal_id + "," + g.Salary);
            }
        }
    }
}
