using System.ComponentModel.DataAnnotations;
namespace ConsoleApp1
{
    class UseDataAnnotations
    {
        [Required(ErrorMessage = "Name is compulsory")]
        [StringLength(20)]
        [RegularExpression(@"^[A-Z]{5, 20}$")]
        [DataType(DataType.Text)]
        public string Name { get; set; }

        [Range(0,120, ErrorMessage ="Age between 0 to 120")]
        public int Age { get; set; }

        [DataType(DataType.PhoneNumber)]
        [Phone]
        public string Cell { get; set; }

        [DataType(DataType.EmailAddress)]
        [EmailAddress]
        public string Email { get; set; }
    }
}
