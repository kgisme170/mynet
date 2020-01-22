using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataApp1
{
    class UseAttribute
    {
        static void Test(string[] args)
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
    [AttributeUsage(AttributeTargets.Property)]
    public class StringLengthAttribute : Attribute
    {
        private readonly int _maximumLength;
        public StringLengthAttribute(int maximumLength)
        {
            _maximumLength = maximumLength;
        }

        public int MaximumLength
        {
            get { return _maximumLength; }
        }
    }
    public class People
    {
        [StringLength(5)]
        public string Name { get; set; }

        [StringLength(20)]
        public string Description { get; set; }
    }
    public class ValidationModel
    {
        public void Validate(object obj)
        {
            var t = obj.GetType();

            var properties = t.GetProperties();
            foreach (var property in properties)
            {
                if (!property.IsDefined(typeof(StringLengthAttribute), false)) continue;

                var attributes = property.GetCustomAttributes(true);
                foreach (var attribute in attributes)
                {
                    var maxinumLength = (int)attribute.GetType().
                      GetProperty("MaximumLength").
                      GetValue(attribute);

                    if (!(property.GetValue(obj) is string propertyValue))
                    {
                        throw new Exception("exception info");
                    }

                    if (propertyValue.Length > maxinumLength)
                        throw new Exception(string.Format("Attribute {0} value {1} length has exceeded {2}", property.Name, propertyValue, maxinumLength));
                }
            }
        }
    }
}
