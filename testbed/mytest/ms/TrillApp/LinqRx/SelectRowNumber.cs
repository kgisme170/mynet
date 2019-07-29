using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
namespace LinqRx
{
    class Person
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
    }

    class Pet
    {
        public string Name { get; set; }
        public Person Owner { get; set; }
    }

    class OrderBySelect
    {
        public static void Test()
        {
            string[] fruits = { "apple", "banana", "mango", "orange", "passionfruit", "grape" };

            var f = fruits.Select((fruit, index) =>
                          new { index, str = fruit.Substring(0, index) });
            f.Print();

            Person magnus = new Person { FirstName = "Magnus", LastName = "Hedlund" };
            Person terry = new Person { FirstName = "Terry", LastName = "Adams" };
            Person charlotte = new Person { FirstName = "Charlotte", LastName = "Weiss" };
            Person arlene = new Person { FirstName = "Arlene", LastName = "Huff" };

            var people = new List<Person>() {
                magnus, terry, charlotte, arlene
            };

            var pets = new List<Pet>() {
                new Pet { Name = "Barley", Owner = terry },
                new Pet { Name = "Boots", Owner = terry },
                new Pet { Name = "Whiskers", Owner = charlotte },
                new Pet { Name = "Blue Moon", Owner = terry },
                new Pet { Name = "Daisy", Owner = magnus }
            };

            XElement ownersAndPets = new XElement("PetOwners",
                from person in people
                join pet in pets on person equals pet.Owner into gj
                select new XElement("Person",
                                    new XAttribute("FirstName", person.FirstName),
                                    new XAttribute("LastName", person.LastName),
                                    from subpet in gj select new XElement("Pet", subpet.Name))
            );
            Console.WriteLine(ownersAndPets);
        }
    }
}
