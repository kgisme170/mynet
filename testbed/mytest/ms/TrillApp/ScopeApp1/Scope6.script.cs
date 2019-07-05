﻿using ScopeRuntime;
using System.Collections.Generic;
using System.Text;
public class DoNothingFunctionResolver : ScopeRuntime.FunctionResolver
{
    public override string Resolve(string name, IEnumerable<Parameter> parameters, string[] args)
    {
        var rowsets = parameters.OfType<RowSetParameter>().ToDictionary(p => p.Name);
        var scalars = parameters.OfType<ScalarParameter>().ToDictionary(p => p.Name);

        var input_rowset = rowsets["input"];
        string input_schema_str = input_rowset.Schema.ToString();
        string output_schema_str = input_rowset.Schema.ToString();
        string select_clause = "*";

        //Generate
        var sb_def = new StringBuilder();
        sb_def.AppendFormat("FUNC {0}\n", name);
        sb_def.AppendFormat(" RETURN ROWSET({0})\n", output_schema_str);
        sb_def.AppendFormat(" PARAMS({0} ROWSET( {1}) )\n", input_rowset.Name, input_schema_str);
        sb_def.AppendFormat("BEGIN\n");
        sb_def.AppendFormat(" rs1=SELECT {0} FROM {1};\n", select_clause, input_rowset.Name);
        sb_def.AppendFormat("END FUNC\n");
        string def = sb_def.ToString();
        return def;
    }
}
public class ReverseSchemaFunctionResolver : ScopeRuntime.FunctionResolver
{
    public override string Resolve(string name, IEnumerable<Parameter> parameters, string[] args)
    {
        var rowsets = parameters.OfType<RowSetParameter>().ToDictionary(p => p.Name);
        var scalars = parameters.OfType<ScalarParameter>().ToDictionary(p => p.Name);

        var input_rowset = rowsets["input"];
        var input_schema = input_rowset.Schema;
        var output_cols = input_schema.Columns.Reverse().ToList();
        var output_schema = new Schema();
        output_schema.AddRange(output_cols);

        string input_schema_str = input_rowset.Schema.ToString();
        string output_schema_str = output_schema.ToString();

        string select_clause = string.Join(",", output_cols.Select(c => c.Name));

        //Generate
        var sb_def = new StringBuilder();
        sb_def.AppendFormat("FUNC {0}\n", name);
        sb_def.AppendFormat(" RETURN ROWSET({0})\n", output_schema_str);
        sb_def.AppendFormat(" PARAMS({0} ROWSET( {1}) )\n", input_rowset.Name, input_schema_str);
        sb_def.AppendFormat("BEGIN\n");
        sb_def.AppendFormat(" rs1=SELECT {0} FROM {1};\n", select_clause, input_rowset.Name);
        sb_def.AppendFormat("END FUNC\n");
        string def = sb_def.ToString();
        return def;
    }
}
