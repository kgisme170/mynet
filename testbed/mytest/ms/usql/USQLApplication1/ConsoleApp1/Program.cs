using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace ConsoleApp1
{
    class Program
    {
        public static void Main(String [] args)
        { 
            var u = new UseGeneric<int>();
            Console.WriteLine(u.Compare(1, 2));
            Console.WriteLine("----------");

            UseDataAnnotations obj = new UseDataAnnotations();
            obj.Name = "abc";
            var context = new ValidationContext(obj, null, null);
            var result = new List<ValidationResult>();
            bool IsValid = Validator.TryValidateObject(
                obj,
                context,
                result, // can be null, 
                true);
            Console.WriteLine(IsValid);
            foreach(var x in result)
            {
                Console.WriteLine(x.ErrorMessage);
            }
        }
    }
}