using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AutoMapper;
namespace ConsoleApp1
{
    class Person
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
    }
    class Employee
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Salary { get; set; }
    }
    class UseMapper
    {
        public static void Test()
        {
            var config = new MapperConfiguration(cfg => cfg.CreateMap<Person, Employee>());
            var mapper = config.CreateMapper();
            Person p = new Person()
            {
                FirstName = "j",
                LastName = "l"
            };
            Employee emp = mapper.Map<Employee>(p);
        }
    }
}
