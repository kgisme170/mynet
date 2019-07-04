using ScopeRuntime;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

public class MyTsvExtractor : Extractor
{
    public override Schema Produces(string[] schema, string[] args)
    {
        return new Schema(@"UserId:int,Start:DateTime,Region:string,Query:string,Duration:int,Urls:string,ClickedUrls:string");
    }
    public override IEnumerable<Row> Extract(StreamReader reader, Row outputRow, string[] args)
    {
        char delimiter = '\t';
        string line;
        while ((line = reader.ReadLine()) != null)
        {
            string[] tokens = line.Split(delimiter); // Consider using IndexOf() or IndexOfAny() instead of Split() for your extractor for improved performance
            for (int i = 0; i < tokens.Length; ++i)
            {
                outputRow[i].UnsafeSet(tokens[i]);
            }
            yield return outputRow;
        }
    }
}

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