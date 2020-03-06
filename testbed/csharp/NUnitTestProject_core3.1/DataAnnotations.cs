using NUnit.Framework;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace NUnitTestProject_core
{
    class DataAnnotations
    {
        [Required(ErrorMessage = "Name is compulsory")]
        [StringLength(20)]
        [RegularExpression(@"^[A-Z]{5, 20}$")]
        [DataType(DataType.Text)]
        public string Name { get; set; } = string.Empty;

        [System.ComponentModel.DataAnnotations.Range(0, 120, ErrorMessage = "Age between 0 to 120")]
        public int Age { get; set; }

        [DataType(DataType.PhoneNumber)]
        [Phone]
        public string Cell { get; set; } = string.Empty;

        [DataType(DataType.EmailAddress)]
        [EmailAddress]
        public string Email { get; set; } = string.Empty;

        [Test]
        public static void TestDataAnnotations()
        {
            DataAnnotations obj = new DataAnnotations
            {
                Name = "abc"
            };
            var context = new ValidationContext(obj, null, null);
            var result = new List<ValidationResult>();
            bool IsValid = Validator.TryValidateObject(
                obj,
                context,
                result, // can be null, 
                true);
            Assert.False(IsValid);
        }
    }
}
