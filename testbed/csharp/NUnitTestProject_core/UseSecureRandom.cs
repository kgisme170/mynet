using System;
using System.Security.Cryptography;

namespace NUnitTestProject_core
{
    class UseSecureRandom
    {
        public static string GetSecureRandom()
        {
            using var rng = new RNGCryptoServiceProvider();
            byte[] randomNumber = new byte[10000];
            rng.GetBytes(randomNumber);
            int value = BitConverter.ToInt32(randomNumber, 0);
            return value.ToString();
        }
    }
}
