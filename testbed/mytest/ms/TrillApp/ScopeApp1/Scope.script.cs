using System;

[Serializable]
class MyType
{
    public string MyProperty { get; set; }
    public MyType(string p)
    {
        MyProperty = p;
    }
}

public static class MyHelper
{
    public static double SecondsToMinutes(int seconds)
    {
        double minutes = seconds / 60.0;
        return minutes;
    }
}
/*
public static class MyExtensions
{
    public static List<string> GetHttpUrls(this IList<string> urls)
    {
        return urls.Where(u => u.StartsWith("http:")).ToList();
    }
}
*/