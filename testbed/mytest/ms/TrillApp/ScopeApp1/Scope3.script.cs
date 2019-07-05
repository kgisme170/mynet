using ScopeRuntime;
using System.Collections.Generic;
using System.IO;
public class MyTsvExtractor : Extractor
{
    public override Schema Produces(string[] schema, string[] args)
    {
        return new Schema(@"UserId:int,name:string");
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
public class MyCsvOutputter : Outputter
{
    public override void Output(RowSet input, StreamWriter writer, string[] args)
    {
        foreach (Row row in input.Rows)
        {
            int c = 0;
            for (int i = 0; i < row.Count; i++)
            {
                if (c > 0)
                {
                    writer.Write(",");
                }
                row[i].Serialize(writer);
                c++;
            }
            writer.WriteLine();
            writer.Flush();
        }
    }
}