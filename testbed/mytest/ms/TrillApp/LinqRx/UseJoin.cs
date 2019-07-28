using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinqRx
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

            var ret3 = from e in employees
                       where !(from d in departments select d.DeptId).Contains(e.DeptId)
                       select e.EmpName;//new { e.EmpName };
            ret3.Print();

            var ret4 = from e in employees select e.EmpName;
            ret4.Print();
            var ret5 = employees.Select(e => e.EmpName);
            ret5.Print();

            var ret6 = employees
                .Where(e => !departments.Select(d => d.DeptId).Contains(e.DeptId))
                .Select(e => e.EmpName);
            ret6.Print();

            var ret7 = from e in employees
                       join d in departments
                       on e.DeptId equals d.DeptId into gj
                       from subdept in gj.DefaultIfEmpty() // left outer join
                       select new { e.EmpName, subdept?.DeptName };
            ret7.Print();

            var ret8 = from e in employees
                       join d in departments on e.DeptId equals d.DeptId into gj
                       from subdept in gj.DefaultIfEmpty()
                       where subdept is null
                       select new { e.EmpName, subdept?.DeptName };
            ret8.Print();
        }
    }
}
