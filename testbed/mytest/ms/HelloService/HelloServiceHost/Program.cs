using System;
using System.ServiceModel;
namespace HelloServiceHost
{
    class Program
    {
        static void Main(string[] args)
        {
            using(ServiceHost serviceHost = new ServiceHost(typeof(HelloService.HelloService)))
            {
                serviceHost.Open();
                Console.WriteLine("service starts");
                Console.ReadLine();
            }
        }
    }
}
