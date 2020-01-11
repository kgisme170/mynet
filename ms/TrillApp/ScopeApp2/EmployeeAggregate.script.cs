using ScopeRuntime;
using System.Collections.Generic;
using System.IO;

///UDT
public class VersionExtractor : Extractor
{
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
            output_row[0].UnsafeSet(tokens[0]);
            output_row[1].UnsafeSet(new MyDepartment(tokens[1]));
            yield return output_row;
        }
    }
}

public class MyDepartment
{
    private string _department;
    public string DeptName
    {
        get { return _department; }
    }

    public int CompareTo(MyDepartment dept)
    {
        return _department.CompareTo(dept._department);
    }

    public MyDepartment(string vString)
    {
        _department = vString;
    }

    public void Serialize(StreamWriter writer)
    {
        writer.Write(_department);
    }
}

public class MyOutputter : Outputter
{
    public override void Output(RowSet input, StreamWriter writer, string[] args)
    {
        foreach (Row row in input.Rows)
        {
            ColumnData col0 = row[0];
            col0.Serialize(writer);
            writer.Write('\t');
            ColumnData col1 = row[1];
            ((MyDepartment)col1.Value).Serialize(writer);
            writer.WriteLine(); 
            writer.Flush();
        }
    }
}