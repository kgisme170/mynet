using ScopeRuntime;
using System.Collections.Generic;

public class MyReducer : Reducer
{
    public override Schema Produces(string[] columns, string[] args, Schema input)
    {
        return new Schema("EmpNames:string,DeptId:int");
    }

    public override IEnumerable<Row> Reduce(RowSet input, Row output, string[] args)
    {
        string names = "";
        int DeptId = 0;
        foreach (Row row in input.Rows)
        {
            names += (string)row[0].Value;
            names += ",";
            DeptId = (int)row[1].Value;
        }
        output[0].Set(names);
        output[1].Set(DeptId);
        yield return output;
    }
}
