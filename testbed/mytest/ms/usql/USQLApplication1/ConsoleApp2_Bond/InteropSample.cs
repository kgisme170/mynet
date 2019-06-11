using System;
using System.Runtime.InteropServices;

namespace ConsoleApp2_Bond
{
    [StructLayout(LayoutKind.Explicit, Size = 16, CharSet = CharSet.Ansi)]
    public class MySystemTime
    {
        [FieldOffset(0)] public ushort wYear;
        [FieldOffset(2)] public ushort wMonth;
        [FieldOffset(4)] public ushort wDayOfWeek;
        [FieldOffset(6)] public ushort wDay;
        [FieldOffset(8)] public ushort wHour;
        [FieldOffset(10)] public ushort wMinute;
        [FieldOffset(12)] public ushort wSecond;
        [FieldOffset(14)] public ushort wMilliseconds;
    }

    internal static class NativeMethods
    {
        [DllImport("kernel32.dll")]
        internal static extern void GetSystemTime([MarshalAs(UnmanagedType.LPStruct)]MySystemTime st);
    };
    class InteropSample
    {
        public static void Test()
        {
            try
            {
                MySystemTime sysTime = new MySystemTime();
                NativeMethods.GetSystemTime(sysTime);
                Console.WriteLine("System time is {0}/{1}/{2} {3}:{4}:{5}", sysTime.wDay,
                   sysTime.wMonth, sysTime.wYear, sysTime.wHour, sysTime.wMinute, sysTime.wSecond);
            }
            catch (TypeLoadException e)
            {
                Console.WriteLine("TypeLoadException : " + e.Message);
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception : " + e.Message);
            }
        }
    }
}
