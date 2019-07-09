using ScopeRuntime;
using System.IO;
using System.Collections.Generic;
public class MyTsvExtractor : Extractor
{
    /*
    public override Schema Produces(string[] schema, string[] args)
    {
        return new Schema(@"UserId:int,name:string");
    }
    */
    public override Schema Produces(string[] requested_columns, string[] args)

    {
        return new Schema(requested_columns);
    }
    public override IEnumerable<Row> Extract(StreamReader reader, Row output_row, string[] args)
    {
        char delimiter = '\t';
        string line;
        while ((line = reader.ReadLine()) != null)
        {
            var tokens = line.Split(delimiter);
            for (int i = 0; i < tokens.Length; ++i)
            {
                output_row[i].UnsafeSet(tokens[i]);
            }
            yield return output_row;
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
                string debug_msg = string.Format("Line {0}: {1}", i, row);
                ScopeRuntime.Diagnostics.DebugStream.WriteLine(debug_msg);
            }
            writer.WriteLine();
            writer.Flush();
        }
    }
}