using ScopeRuntime;
using System.IO;

public class MyOutputter : Outputter
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