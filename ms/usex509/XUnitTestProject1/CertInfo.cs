using System;
using System.Diagnostics;
using System.IO;
using System.Security.Cryptography.X509Certificates;

// from : https://docs.microsoft.com/zh-cn/dotnet/api/system.security.cryptography.x509certificates.x509certificate2.serialnumber?view=netframework-4.8
namespace XUnitTestProject1
{
    class CertInfo
    {
        //Reads a file.
        internal static byte[] ReadFile(string fileName)
        {
            FileStream f = new FileStream(fileName, FileMode.Open, FileAccess.Read);
            int size = (int)f.Length;
            byte[] data = new byte[size];
            f.Read(data, 0, size);
            f.Close();
            return data;
        }
        //Main method begins here.
        public static void Run(string file)
        {
            //Test for correct number of arguments.
            try
            {
                /*
                X509Certificate2 x509 = new X509Certificate2();
                //Create X509Certificate2 object from .cer file.
                byte[] rawData = ReadFile(file);
                x509.Import(rawData);
                */
                var x509 = new X509Certificate2(file);
                //Debug.WriteLine to console information contained in the certificate.
                Debug.WriteLine(Environment.NewLine);
                Debug.WriteLine("Subject: {1}{0}", Environment.NewLine, x509.Subject);
                Debug.WriteLine("Issuer: {1}{0}", Environment.NewLine, x509.Issuer);
                Debug.WriteLine("Version: {1}{0}", Environment.NewLine, x509.Version);
                Debug.WriteLine("Valid Date: {1}{0}", Environment.NewLine, x509.NotBefore);
                Debug.WriteLine("Expiry Date: {1}{0}", Environment.NewLine, x509.NotAfter);
                Debug.WriteLine("ThumbDebug.WriteLine: {1}{0}", Environment.NewLine, x509.Thumbprint);
                Debug.WriteLine("Serial Number: {1}{0}", Environment.NewLine, x509.SerialNumber);
                Debug.WriteLine("Friendly Name: {1}{0}", Environment.NewLine, x509.PublicKey.Oid.FriendlyName);
                Debug.WriteLine("Public Key Format: {1}{0}", Environment.NewLine, x509.PublicKey.EncodedKeyValue.Format(true));
                Debug.WriteLine("Raw Data Length: {1}{0}", Environment.NewLine, x509.RawData.Length);
                Debug.WriteLine("Certificate to string: {1}{0}", Environment.NewLine, x509.ToString(true));
                //Debug.WriteLine("Certificate to XML String: {1}{0}", Environment.NewLine, x509.PublicKey.Key.ToXmlString(false));

                //Add the certificate to a X509Store.
                X509Store store = new X509Store();
                store.Open(OpenFlags.MaxAllowed);
                store.Add(x509);
                store.Close();
            }
            catch (DirectoryNotFoundException)
            {
                Debug.WriteLine("Error: The directory specified could not be found.");
            }
            catch (IOException)
            {
                Debug.WriteLine("Error: A file in the directory could not be accessed.");
            }
            catch (NullReferenceException)
            {
                Debug.WriteLine("File must be a .cer file. Program does not have access to that type of file.");
            }
        }
    }
}
