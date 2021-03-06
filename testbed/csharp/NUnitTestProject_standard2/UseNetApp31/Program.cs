﻿using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Security;
using System.Security.Cryptography.X509Certificates;
using VcClient;

/*
 * Test parameter setup:
 * (1) Prod: Copy d:\copy.json https://cosmos08.osdinfra.net/cosmos/adcenter.bicore.prod2 xx d:\copy.result.json
 * (2) Dev: Copy d:\copy_dev.json https://cosmos08.osdinfra.net/cosmos/sharedData.Ads.Dev xx d:\copy_dev.result.json
 *          Move d:\move_dev.json https://cosmos08.osdinfra.net/cosmos/sharedData.Ads.Dev xx d:\move_dev.result.json
 *          List d:\list_dev.json https://cosmos08.osdinfra.net/cosmos/sharedData.Ads.Dev xx d:\list_dev.result.json
 *          Delete d:\delete_dev.json https://cosmos08.osdinfra.net/cosmos/sharedData.Ads.Dev xx d:\delete_dev.result.json
 *          ChangeExpiryTime d:\expiry_dev.json https://cosmos08.osdinfra.net/cosmos/sharedData.Ads.Dev xx d:\expiry_dev.result.json
 */

namespace UseNetApp31
{
    class Program
    {
        // private const string vcName = @"https://cosmos08.osdinfra.net/cosmos/sharedData.Ads.Dev/";
        private const string vcName = @"https://cosmos08.osdinfra.net:443/cosmos/adcenter.bicore.prod2";

        // private const string thumbprint = @"4ae99066ff4c9c45f2a0f5ad699c758000bd35db"; // dev
        private const string thumbprint = @"02fb39616d412c39392293096dcd3f881f4d7072"; // prod

        private static string TrimUrlAddress(string urlString)
        {
            int index = 0;
            int count = 0;
            var chars = urlString.ToCharArray();
            foreach (var c in chars)
            {
                ++index;
                if (c == '/')
                {
                    ++count;
                }
                if (count == 3)
                {
                    break;
                }
            }

            if (count != 3)
            {
                return string.Empty;
            }

            return urlString[index..];
        }

        private static string RelativePath(string vcName, string entryName)
        {
            var trimmedVcName = TrimUrlAddress(vcName); // cosmos/adcenter.bicore.prod2
            var trimmedEntryName = TrimUrlAddress(entryName); // cosmos/adcenter.bicore.prod2/dir01/file01
            return trimmedEntryName[trimmedVcName.Length..];
        }

        public static void Main(string[] args)
        {
            TrySetupUsingCertThumbprint(vcName, thumbprint);
            // https://stackoverflow.com/questions/2960056/trying-to-run-multiple-http-requests-in-parallel-but-being-limited-by-windows

            ServicePointManager.DefaultConnectionLimit = 1000;
            var entryName = @"https://aad.cosmos08.osdinfra.net:443/cosmos/adcenter.bicore.prod2/users/limgong/dir1/file01";
            var relativePath = RelativePath(vcName, entryName);
            Console.WriteLine(relativePath);

            // var clusterPath = @"local/users/limgong/dir1";
            // TraverseDirectory(clusterPath);

            // 所有streaming名字都是类似 /local/path/to/stream这种
            // -> ui上做了一些trick，让你能够浏览所有前缀为 /local的stream
            // -> 可以move一个文件比如log.txt成为dir1/dir2/dir3/log.txt，而我不需要事先创建dir1/dir2/dir3这样的目录结构。

            string from = @"users/limgong/testdir1/log2.txt";
            string to = relativePath;
            CopyFile(from, to);

            // string from = @"local/users/limgong/log.txt";
            // string to = @"local/users/limgong/dir1/dir1a/dir1b/log.txt";
            // MoveFile(from, to);
            // CopyFile(from, to);
            // DeleteFile(from);
            // DateTime oneMonthLater = DateTime.Now.AddDays(30);
            // ChangeExpiryTime(to, oneMonthLater);
        }

        public static void ChangeExpiryTime(string fileName, DateTime dateTime)
        {
            try
            {
                VC.SetStreamExpirationTime(fileName, dateTime - DateTime.Now);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            Console.WriteLine($"ChangeExpiryTime {fileName} to expiry time of {dateTime} ok");
        }

        static void DeleteFile(string fn)
        {
            try
            {
                VC.Delete(fn);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            Console.WriteLine($"{fn} deleted");
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
            Console.WriteLine($"{from} moved to {to}");
        }

        static void CopyFile(string from, string to)
        {
            try
            {
                VC.Concatenate(from, to);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            Console.WriteLine($"{from} copied to {to}");
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
            SecureString password)
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
            SecureString password)
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
