﻿// Install-Package AutoMapper -Version 9.0.0
using AutoMapper;
using NUnit.Framework;

namespace NUnitTestProject_core
{
    class CreateMapper
    {
        class Person
        {
            public string FirstName { get; set; } = string.Empty;
            public string LastName { get; set; } = string.Empty;
        }
        class Employee
        {
            public string FirstName { get; set; } = string.Empty;
            public string LastName { get; set; } = string.Empty;
            public string Salary { get; set; } = string.Empty;
        }
        [Test]
        public static void TestCreateMapper()
        {
            var config = new MapperConfiguration(cfg => cfg.CreateMap<Person, Employee>());
            var mapper = config.CreateMapper();
            Person p = new Person()
            {
                FirstName = "j",
                LastName = "l"
            };
            Employee emp = mapper.Map<Employee>(p);
            Assert.AreEqual("j", emp.FirstName);
        }
    }
}
