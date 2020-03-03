using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Security;
using System.Security.Cryptography.X509Certificates;
using VcClient;

namespace UseNetApp31
{
    class Program
    {
        private const string vcName = @"https://cosmos08.osdinfra.net/cosmos/sharedData.Ads.Dev/";
        private const string thumbprint = @"4ae99066ff4c9c45f2a0f5ad699c758000bd35db";

        public static void Main(string[] args)
        {
            TrySetupUsingCertThumbprint(vcName, thumbprint);

            var clusterPath = @"local/users/limgong/dir1";
            TraverseDirectory(clusterPath);

            string from = @"local/users/limgong/dir1/log.txt";
            string to = @"local/users/limgong/dir2/log.txt";
            MoveFile(from, to);
        }

        static void MoveFile(string from, string to)
        {
            try
            {
                VC.Rename(from, to);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        static void TraverseDirectory(string dir)
        {
            List<StreamInfo> streams = VC.GetDirectoryInfo(dir, false);
            int count = 0;
            foreach (var stream in streams)
            {
                var sName = stream.StreamName;
                if (Path.GetFileName(sName).Equals(@"__placeholder__", StringComparison.CurrentCultureIgnoreCase))
                {
                    continue;
                }

                ++count;
                var subEntry = sName[vcName.Length..];
                Console.WriteLine(subEntry);
                if (stream.IsDirectory)
                {
                    TraverseDirectory(sName);
                }
            }
            Console.WriteLine("dir has {0} subentries", count);
        }

        public static void SetupUsingCertFile(
            string vcName,
            string certFilePath,
            SecureString password = null)
        {
            if (string.IsNullOrEmpty(vcName))
            {
                throw new ArgumentException("Empty vc name", nameof(vcName));
            }

            if (string.IsNullOrEmpty(certFilePath))
            {
                throw new ArgumentException("Empty cert file path", nameof(certFilePath));
            }

            if (!File.Exists(certFilePath))
            {
                throw new FileNotFoundException($"Specified cert file {certFilePath} not found");
            }

            Trace.TraceInformation($"Trying to load certificate from {certFilePath}");
            var cert = password == null
                ? new X509Certificate2(certFilePath)
                : new X509Certificate2(certFilePath, password);
            VC.Setup(vcName, cert);
        }

        public static void SetupUsingCertThumbprint(
            string vcName,
            string thumbprint,
            StoreLocation storeLocation = StoreLocation.LocalMachine,
            StoreName storeName = StoreName.My)
        {
            if (string.IsNullOrEmpty(vcName))
            {
                throw new ArgumentException("Empty vc name", nameof(vcName));
            }

            if (string.IsNullOrEmpty(thumbprint))
            {
                throw new ArgumentException("Empty cert thumbprint", nameof(thumbprint));
            }

            Trace.TraceInformation($"Trying to find cert with thumbprint {thumbprint} under {storeLocation}/{storeName}");
            var certStore = new X509Store(storeName, storeLocation);
            certStore.Open(OpenFlags.ReadOnly | OpenFlags.OpenExistingOnly);
            var certCollection = certStore.Certificates.Find(X509FindType.FindByThumbprint, thumbprint, true);
            if (certCollection.Count <= 0)
            {
                throw new ArgumentException($"Cert with thumbprint {thumbprint} not found under {storeLocation}/{storeName}");
            }

            var cert = certCollection[0];
            VC.Setup(vcName, cert);
        }

        public static bool TrySetupUsingCertFile(
            string vcName,
            string certFilePath,
            SecureString password = null)
        {
            try
            {
                SetupUsingCertFile(vcName, certFilePath, password);
                return true;
            }
            catch
            {
                return false;
            }
        }

        public static bool TrySetupUsingCertThumbprint(
            string vcName,
            string thumbprint,
            StoreLocation storeLocation = StoreLocation.LocalMachine,
            StoreName storeName = StoreName.My)
        {
            try
            {
                SetupUsingCertThumbprint(vcName, thumbprint, storeLocation, storeName);
                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return false;
            }
        }
    }
}
