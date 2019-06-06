using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            var people = new People()
            {
                Name = "asdfasdfasdfasdfasdfasdf",
                Description = "description"
            };
            try
            {
                new ValidationModel().Validate(people);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
    }
}
