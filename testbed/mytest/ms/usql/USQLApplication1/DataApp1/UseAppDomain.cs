using System;
using System.Security;
using System.Security.Permissions;

namespace DataApp1
{
    class Class1
    {

    }
    class Class2
    {

    }
    class UseAppDomain
    {
        public static void Test()
        {
            var perm = new PermissionSet(PermissionState.None);
            perm.AddPermission(
                new SecurityPermission(SecurityPermissionFlag.Execution));
            perm.AddPermission(
                new FileIOPermission(FileIOPermissionAccess.NoAccess, "@d:\\"));

            var setup = new AppDomainSetup();
            setup.ApplicationBase = AppDomain.CurrentDomain.SetupInformation.ApplicationBase;
            AppDomain secureDomain = AppDomain.CreateDomain("secure", null, setup, perm);

            // App domain
            ThirdParty third = new ThirdParty();

            Type thirdParty = typeof(ThirdParty);
            secureDomain.
                CreateInstanceAndUnwrap(thirdParty.Assembly.FullName,
                    thirdParty.FullName);
            AppDomain.Unload(secureDomain);

            Class1 obj1 = new Class1();
            Class2 obj2 = new Class2();

            // current domain
            Console.Read();
        }
    }

    [Serializable]
    class ThirdParty
    {
        public ThirdParty()
        {
            Console.WriteLine("3p loadling");
            System.IO.File.Create(@"d:\x.txt");
        }
        ~ThirdParty()
        {
            Console.WriteLine("3p unloaded");
        }
    }
}
