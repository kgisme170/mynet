using System.ComponentModel.DataAnnotations;
namespace ConsoleApp1
{
    class UseDataAnnotations
    {
        [Required(ErrorMessage = "Name is compulsory")]
        [StringLength(20)]
        [RegularExpression(@"^[A-Z]{5, 20}$")]
        public string Name { get; set; }
    }
}
