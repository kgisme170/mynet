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
        public int Employ_id { get; set; }
        public string First_name { get; set; }
        public string Last_name { get; set; }
        public Gender gender { get; set; }
        public int Department_id { get; set; }
        public int Salary { get; set; }
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
                    Employ_id=2002,
                    First_name="Super",
                    Last_name="Man",
                    gender=Gender.M,
                    Department_id=1,
                    Salary=75000
                },
                new Employee
                {
                    Employ_id=2003,
                    First_name="Jessica",
                    Last_name="Liyers",
                    gender=Gender.F,
                    Department_id=1,
                    Salary=60000
                },
                new Employee
                {
                    Employ_id=2004,
                    First_name="Bonnie",
                    Last_name="Adams",
                    gender=Gender.M,
                    Department_id=1,
                    Salary=55000
                },
                new Employee
                {
                    Employ_id=2005,
                    First_name="James",
                    Last_name="Bakon",
                    gender=Gender.M,
                    Department_id=2,
                    Salary=65000
                },
                new Employee
                {
                    Employ_id=2006,
                    First_name="Michael",
                    Last_name="Robins",
                    gender=Gender.M,
                    Department_id=2,
                    Salary=60000
                },
                new Employee
                {
                    Employ_id=2007,
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

            var employ_between_2003_2008 = ie.Where(x => x.Employ_id >= 2003 && x.Employ_id <=2008);
            foreach(var e in employ_between_2003_2008)
            {
                Console.WriteLine(e);
            }
        }
    }
}
