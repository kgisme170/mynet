﻿// ------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version: 16.0.0.0
//  
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
// ------------------------------------------------------------------------------
namespace Microsoft.StreamProcessing
{
    using System.Linq;
    using System.Collections.Generic;
    using System;
    
    /// <summary>
    /// Class to produce the template output
    /// </summary>
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.VisualStudio.TextTemplating", "16.0.0.0")]
    internal partial class UngroupTemplate : CommonPipeTemplate
    {
        /// <summary>
        /// Create the template output
        /// </summary>
        public override string TransformText()
        {
            this.Write(@"// *********************************************************************
// Copyright (c) Microsoft Corporation.  All rights reserved.
// Licensed under the MIT License
// *********************************************************************
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Runtime.Serialization;
using Microsoft.StreamProcessing;
using Microsoft.StreamProcessing.Internal;
using Microsoft.StreamProcessing.Internal.Collections;
[assembly: IgnoresAccessChecksTo(""Microsoft.StreamProcessing"")]

// OuterKey: ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TOuterKey));
            this.Write("\r\n// InnerKey: ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TInnerKey));
            this.Write("\r\n// InnerResult: ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TInnerResult));
            this.Write("\r\n// TResult: ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TResult));
            this.Write("\r\n\r\n");

    var memoryPoolGenericParameters = string.Format("<{0}, {1}>", TOuterKey, TResult);
    if (resultType.MemoryPoolHasGetMethodFor())
        memoryPoolGenericParameters = string.Empty;
    var memoryPoolClassName = Transformer.GetMemoryPoolClassName(this.outerKeyType, this.resultType) + memoryPoolGenericParameters;

    var inputKey = ungroupingFromCompound ? "CompoundGroupKey<" + TOuterKey + ", " + TInnerKey + ">" : TInnerKey;

            this.Write("\r\n[DataContract]\r\ninternal sealed class ");
            this.Write(this.ToStringHelper.ToStringWithCulture(className));
            this.Write(this.ToStringHelper.ToStringWithCulture(genericParameters));
            this.Write(" :\r\n                       Pipe<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TOuterKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TResult));
            this.Write(">, IStreamObserver<");
            this.Write(this.ToStringHelper.ToStringWithCulture(inputKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TInnerResult));
            this.Write(">\r\n{\r\n    private readonly ");
            this.Write(this.ToStringHelper.ToStringWithCulture(memoryPoolClassName));
            this.Write(" outPool;\r\n    private readonly Func<PlanNode, IQueryObject, PlanNode> queryPlanG" +
                    "enerator;\r\n\r\n");
 if (ungroupingToUnit) { 
            this.Write("    private readonly ColumnBatch<Microsoft.StreamProcessing.Empty> unitColumn;\r\n " +
                    "   private readonly ColumnBatch<int> unitHashColumn;\r\n");
 } 
 else { 
            this.Write("    private readonly Func<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TOuterKey));
            this.Write(", int> outerHashCode;\r\n");
 } 
 foreach (var f in this.unassignedFields) { 
            this.Write("    private readonly ColumnBatch<");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.TypeName));
            this.Write("> sharedDefaultColumnFor_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(";\r\n");
 } 
            this.Write("\r\n    ");
            this.Write(this.ToStringHelper.ToStringWithCulture(staticCtor));
            this.Write("\r\n\r\n    public ");
            this.Write(this.ToStringHelper.ToStringWithCulture(className));
            this.Write("() { }\r\n\r\n    public ");
            this.Write(this.ToStringHelper.ToStringWithCulture(className));
            this.Write("(\r\n        IStreamable<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TOuterKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TResult));
            this.Write("> stream,\r\n        IStreamObserver<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TOuterKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TResult));
            this.Write("> observer,\r\n        Func<PlanNode, IQueryObject, PlanNode> queryPlanGenerator)\r\n" +
                    "        : base(stream, observer)\r\n    {\r\n        this.outPool = MemoryManager.Ge" +
                    "tMemoryPool<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TOuterKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TResult));
            this.Write(">(true) as ");
            this.Write(this.ToStringHelper.ToStringWithCulture(memoryPoolClassName));
            this.Write(";\r\n        this.queryPlanGenerator = queryPlanGenerator;\r\n\r\n");
 if (ungroupingToUnit) { 
            this.Write("        this.outPool.GetKey(out this.unitColumn);\r\n        this.outPool.Get(out t" +
                    "his.unitHashColumn);\r\n        Array.Clear(this.unitHashColumn.col, 0, this.unitH" +
                    "ashColumn.col.Length);\r\n");
 } 
 else { 
            this.Write("        this.outerHashCode = stream.Properties.KeyEqualityComparer.GetGetHashCode" +
                    "Expr().Compile();\r\n");
 } 
 foreach (var f in this.unassignedFields) { 
            this.Write("        this.outPool.Get(out sharedDefaultColumnFor_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(");\r\n        Array.Clear(sharedDefaultColumnFor_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col, 0, sharedDefaultColumnFor_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col.Length);\r\n");
 } 
            this.Write(@"    }

    public override void ProduceQueryPlan(PlanNode previous)
    {
        Observer.ProduceQueryPlan(queryPlanGenerator(previous, this));
    }

    public override int CurrentlyBufferedOutputCount => 0;

    public override int CurrentlyBufferedInputCount => 0;

    protected override void DisposeState()
    {
");
 if (ungroupingToUnit) { 
            this.Write("        this.unitColumn.Return();\r\n        this.unitHashColumn.Return();\r\n");
 } 
 foreach (var f in this.unassignedFields) { 
            this.Write("        this.sharedDefaultColumnFor_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".Return();\r\n");
 } 
            this.Write("    }\r\n\r\n    public unsafe void OnNext(StreamMessage<");
            this.Write(this.ToStringHelper.ToStringWithCulture(inputKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TInnerResult));
            this.Write("> batch)\r\n    {\r\n        ");
            this.Write(this.ToStringHelper.ToStringWithCulture(inputBatchClassType));
            this.Write(this.ToStringHelper.ToStringWithCulture(inputBatchGenericParameters));
            this.Write(" inputBatch = batch as ");
            this.Write(this.ToStringHelper.ToStringWithCulture(inputBatchClassType));
            this.Write(this.ToStringHelper.ToStringWithCulture(inputBatchGenericParameters));
            this.Write(";\r\n\r\n        StreamMessage<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TOuterKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TResult));
            this.Write("> tmp; // Need this type to call Get with so the right subtype will be returned\r\n" +
                    "        outPool.Get(out tmp);\r\n\r\n        ");
            this.Write(this.ToStringHelper.ToStringWithCulture(resultBatchClassType));
            this.Write(this.ToStringHelper.ToStringWithCulture(resultBatchGenericParameters));
            this.Write(" resultBatch = tmp as ");
            this.Write(this.ToStringHelper.ToStringWithCulture(resultBatchClassType));
            this.Write(this.ToStringHelper.ToStringWithCulture(resultBatchGenericParameters));
            this.Write(";\r\n\r\n");
 foreach (var f in this.computedFields.Keys) { 
            this.Write("\r\n        outPool.Get(out resultBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(");\r\n");
 } 
            this.Write("\r\n");
 if (!ungroupingToUnit) { 
            this.Write("\r\n        tmp.hash = batch.hash.MakeWritable(outPool.intPool);\r\n");
 } 
            this.Write("        var count = batch.Count;\r\n\r\n        tmp.vsync = batch.vsync;\r\n        tmp" +
                    ".vother = batch.vother;\r\n        tmp.bitvector = batch.bitvector;\r\n\r\n        // " +
                    "Assign the swinging fields\r\n");
 foreach (var tuple in this.swingingFields) {
        var destField = tuple.Item1.Name;
        var sourceField = tuple.Item2.Name;

            this.Write("\r\n        resultBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(destField));
            this.Write(" = inputBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(sourceField));
            this.Write(";\r\n");
 } 
            this.Write("\r\n");
 if (resultType.CanContainNull()) { 
            this.Write("\r\n        outPool.GetBV(out resultBatch._nullnessvector);\r\n");
 } 
            this.Write("\r\n        fixed (long *srcbv = batch.bitvector.col) {\r\n");
 if (!ungroupingToUnit) { 
            this.Write("        fixed (int *desthash = tmp.hash.col) {\r\n");
 } 
            this.Write("\r\n        // Get pointers to the arrays for the inner result fields\r\n");
 foreach (var f in this.innerResultRepresentation.AllFields) { 
            this.Write("        var ");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("_col = inputBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col;\r\n");
 } 
            this.Write("\r\n        // Get pointers to the arrays for the result fields\r\n");
 foreach (var f in this.computedFields.Keys) { 
     if (f.canBeFixed) { 
            this.Write("        fixed (");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.TypeName));
            this.Write("* dest_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(" = resultBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col) {\r\n");
     } else { 
       if (!f.OptimizeString()) { 
            this.Write("        var dest_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(" = resultBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col;\r\n");
       } else { 
            this.Write("        var dest_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(" = resultBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(";\r\n");
       } 
     } 
 } 
            this.Write("\r\n");
 if (ungroupingToUnit) { 
            this.Write("        this.unitColumn.IncrementRefCount(1);\r\n        tmp.key = unitColumn;\r\n   " +
                    "     this.unitHashColumn.IncrementRefCount(1);\r\n        tmp.hash = unitHashColum" +
                    "n;\r\n");
 } 
            this.Write("\r\n");
 foreach (var f in this.unassignedFields) { 
            this.Write("        this.sharedDefaultColumnFor_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".IncrementRefCount(1);\r\n        resultBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(" = this.sharedDefaultColumnFor_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(";\r\n");
 } 
            this.Write("\r\n");
 if (!ungroupingToUnit || this.computedFields.Any()) { 
     if (!ungroupingToUnit) { 
            this.Write("            var srckey = batch.key.col;\r\n            var destkey = tmp.key.col;\r\n" +
                    "");
     } 
            this.Write("\r\n            for (int i = 0; i < count; i++)\r\n            {\r\n                if " +
                    "((srcbv[i >> 6] & (1L << (i & 0x3f))) != 0)\r\n                {\r\n                " +
                    "    // Need to add empty strings to keep multistring indexing consistent\r\n");
     foreach (var kv in this.computedFields) {
            var field = kv.Key;
            if (field.OptimizeString()) { 
            this.Write("                    dest_");
            this.Write(this.ToStringHelper.ToStringWithCulture(field.Name));
            this.Write(".AddString(string.Empty);\r\n");
          } 
     } 
            this.Write("                    continue;\r\n                }\r\n");
     if (!ungroupingToUnit) { 
            this.Write("                destkey[i] = srckey[i].outerGroup;\r\n                desthash[i] =" +
                    " this.outerHashCode(destkey[i]);\r\n");
     } 
     foreach (var kv in this.computedFields) {
            var f = kv.Key;
            string v;
            var map = new Dictionary<System.Linq.Expressions.ParameterExpression, string>();
            map.Add(this.keyParameter, "batch.key.col[i].InnerGroup");
            v = kv.Value.ExpressionToCSharpStringWithParameterSubstitution(map);

          if (f.OptimizeString()) { 
            this.Write("                        dest_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".AddString(");
            this.Write(this.ToStringHelper.ToStringWithCulture(v));
            this.Write(");\r\n");
          } else { 
            this.Write("                        dest_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("[i] = ");
            this.Write(this.ToStringHelper.ToStringWithCulture(v));
            this.Write(";\r\n");
          } 
     } 
            this.Write("            }\r\n");
 } 
            this.Write("\r\n");
 foreach (var f in this.computedFields.Keys.Where(fld => fld.canBeFixed)) { 
            this.Write("\r\n        } // end of fixed for ");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("\r\n");
 } 
            this.Write("\r\n");
 if (!ungroupingToUnit) { 
            this.Write("\r\n        } // end of fixed for desthash\r\n");
 } 
            this.Write("        } // end of fixed for srcbv\r\n\r\n        tmp.Count = count;\r\n\r\n        batc" +
                    "h.ReleaseKey();\r\n");
 if (ungroupingToUnit) { 
            this.Write("\r\n        batch.hash.Return();\r\n");
 } 
            this.Write("\r\n        #region Return source columns as necessary.\r\n        // This is all fie" +
                    "lds from the input batch *except* for any swinging fields.\r\n");
 foreach (var f in this.innerResultRepresentation.AllFields.Where(f => !this.swingingFields.Any(tup => tup.Item2.Name == f.Name))) { 
     if (f.OptimizeString()) { 
            this.Write("        inputBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".Dispose();\r\n");
     } else { 
            this.Write("        inputBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".Return();\r\n");
     } 
 } 
            this.Write("        #endregion\r\n\r\n");
 if (innerResultType.CanContainNull()) { 
            this.Write("\r\n        inputBatch._nullnessvector.ReturnClear();\r\n");
 } 
            this.Write("\r\n        batch.Return();\r\n\r\n        tmp.Seal();\r\n        this.Observer.OnNext(tm" +
                    "p);\r\n    }\r\n}\r\n");
            return this.GenerationEnvironment.ToString();
        }
    }
}
