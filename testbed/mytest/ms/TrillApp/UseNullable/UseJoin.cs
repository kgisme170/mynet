using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UseNullable
{
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
    public static class MyExtension
    {
        public static void Print<T>(this IEnumerable<T> data)
        {
            foreach ( T t in data)
            {
                Console.WriteLine(t);
            }
            Console.WriteLine("----------");
        }
    }
    class UseJoin
    {
        public static void Test()
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
            result.Print();

            var ret2 = employees.Join(
                departments,
                left => left.DeptId,
                right => right.DeptId,
                (left, right) => new { left.EmpName, right.DeptName });
            ret2.Print();
        }
    }
}
