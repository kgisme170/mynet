using System.Security.Cryptography;
using System;
namespace ConsoleApp1
{
    class SecureRandom
    {
        public static string GetSecureRandom()
        {
            using (RNGCryptoServiceProvider rng = new RNGCryptoServiceProvider())
            {
                byte[] randomNumber = new byte[10000];
                rng.GetBytes(randomNumber);
                int value = BitConverter.ToInt32(randomNumber, 0);
                return value.ToString();
            }
        }
    }
}
