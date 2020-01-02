﻿// ------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version: 15.0.0.0
//  
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
// ------------------------------------------------------------------------------
namespace Microsoft.StreamProcessing
{
    using System.Linq;
    using System.Collections.Generic;
    using System.Reflection;
    using System;
    
    /// <summary>
    /// Class to produce the template output
    /// </summary>
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.VisualStudio.TextTemplating", "15.0.0.0")]
    internal partial class ColumnToRowTemplate : CommonUnaryTemplate
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
using System.IO;
using System.Linq;
using System.Linq.Expressions;
using System.Reflection;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using Microsoft.StreamProcessing;
using Microsoft.StreamProcessing.Internal;
using Microsoft.StreamProcessing.Internal.Collections;

");
 if (this.keyType.Namespace != null) { 
            this.Write("using ");
            this.Write(this.ToStringHelper.ToStringWithCulture(this.keyType.Namespace));
            this.Write(";\r\n");
 } 
 if (this.payloadType.Namespace != null) { 
            this.Write("using ");
            this.Write(this.ToStringHelper.ToStringWithCulture(this.payloadType.Namespace));
            this.Write(";\r\n");
 } 
            this.Write("\r\n");

    List<string> genericParamList = new List<string>();
    var TKey = keyType.GetCSharpSourceSyntax(ref genericParamList);
    var TPayload = payloadType.GetCSharpSourceSyntax(ref genericParamList);
    var genericParameters = 0 < genericParamList.Count ? "<" + String.Join(",", genericParamList) + ">" : string.Empty;
    var payloadIsAnon = payloadType.IsAnonymousType();

            this.Write("\r\n[DataContract]\r\ninternal sealed class ");
            this.Write(this.ToStringHelper.ToStringWithCulture(className));
            this.Write(this.ToStringHelper.ToStringWithCulture(genericParameters));
            this.Write(" : UnaryPipe<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(">\r\n{\r\n    private readonly Func<PlanNode, IQueryObject, PlanNode> queryPlanGenera" +
                    "tor;\r\n    private readonly MemoryPool<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write("> pool;\r\n\r\n");

    if (payloadIsAnon) {
        foreach (var f in this.fields) {

            this.Write("    private PropertyInfo ");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("_Property = typeof(");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(").GetProperty(\"");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("\");\r\n");

        }
    }

            this.Write("\r\n    ");
            this.Write(this.ToStringHelper.ToStringWithCulture(staticCtor));
            this.Write("\r\n\r\n    public ");
            this.Write(this.ToStringHelper.ToStringWithCulture(className));
            this.Write("() { }\r\n\r\n    public ");
            this.Write(this.ToStringHelper.ToStringWithCulture(className));
            this.Write("(\r\n        IStreamable<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write("> stream,\r\n        IStreamObserver<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write("> observer,\r\n        Func<PlanNode, IQueryObject, PlanNode> queryPlanGenerator)\r\n" +
                    "        : base(stream, observer)\r\n    {\r\n        pool = new MemoryPool<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(@">(false);
        this.queryPlanGenerator = queryPlanGenerator;
    }

    public override void ProduceQueryPlan(PlanNode previous)
    {
        Observer.ProduceQueryPlan(queryPlanGenerator(previous, this));
    }

    public override unsafe void OnNext(StreamMessage<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write("> batch)\r\n    {\r\n        ");
            this.Write(this.ToStringHelper.ToStringWithCulture(BatchGeneratedFrom_TKey_TPayload));
            this.Write(this.ToStringHelper.ToStringWithCulture(genericParameters));
            this.Write(" sourceBatch = batch as ");
            this.Write(this.ToStringHelper.ToStringWithCulture(BatchGeneratedFrom_TKey_TPayload));
            this.Write(this.ToStringHelper.ToStringWithCulture(genericParameters));
            this.Write(";\r\n\r\n        StreamMessage<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(@"> resultBatch; // Need this type to call Get with so the right subtype will be returned

        this.pool.Get(out resultBatch);
        resultBatch.CloneFromNoPayload(sourceBatch);
        this.pool.GetPayload(out resultBatch.payload);

        var count = sourceBatch.Count;

        ");
 foreach (var f in this.fields) { 
            this.Write("\r\n        ");
 if (f.canBeFixed) { 
            this.Write("\r\n        fixed (");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.TypeName));
            this.Write("* src_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(" = sourceBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col)\r\n        {\r\n\r\n        ");
 } else { 
            this.Write("\r\n        ");
 if (f.OptimizeString()) { 
            this.Write("        var src_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(" = sourceBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(";\r\n        ");
 } else { 
            this.Write("        var src_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(" = sourceBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col;\r\n        ");
 } 
            this.Write("\r\n        ");
 } 
            this.Write("        ");
 } 
            this.Write("\r\n        fixed (long* bv = sourceBatch.bitvector.col)\r\n        {\r\n        var de" +
                    "stpayload = resultBatch.payload.col;\r\n        {\r\n\r\n            ");
 if (this.rowMajor) { 
            this.Write("\r\n            for (int i = 0; i < count; i++)\r\n            {\r\n                if " +
                    "((bv[i >> 6] & (1L << (i & 0x3f)))==0)\r\n                {\r\n\r\n                   " +
                    " ");
 if (payloadIsAnon) {
                      var fieldArgs = this.fields.Select(f => string.Format(", src_{0}[i]", f.Name)).Aggregate((x, y) => x + y);
                    
            this.Write("\r\n                    destpayload[i] = (");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(")Activator.CreateInstance(typeof(");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(") ");
            this.Write(this.ToStringHelper.ToStringWithCulture(fieldArgs));
            this.Write(" );\r\n                    ");
 } else { 
            this.Write("                    ");
 if (!payloadType.GetTypeInfo().IsValueType) { 
            this.Write("\r\n                    destpayload[i] = new ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write("();\r\n                    ");
 } 
            this.Write("                    ");
 foreach (var f in this.fields) { 
            this.Write("\r\n                    ");
 if (this.noFields) { 
            this.Write("\r\n                    destpayload[i] = src_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("[i];\r\n                    ");
 } else { 
            this.Write("\r\n                    destpayload[i].");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.OriginalName));
            this.Write(" = src_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("[i];\r\n                    ");
 } 
            this.Write("                    ");
 } 
            this.Write("                    ");
 } 
            this.Write("\r\n                }\r\n            }\r\n\r\n            ");
 } else { 
            this.Write("\r\n                ");
 if (payloadIsAnon) {
                    var fieldArgs = this.fields.Select(f => string.Format(", src_{0}[i]", f.Name)).Aggregate((x, y) => x + y);
                
            this.Write("                destpayload[i] = (");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(")Activator.CreateInstance(typeof(");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(") ");
            this.Write(this.ToStringHelper.ToStringWithCulture(fieldArgs));
            this.Write(" );\r\n                ");
 } else { 
            this.Write("                ");
 foreach (var f in this.fields) { 
            this.Write("\r\n                    for (int i = 0; i < count; i++)\r\n                    {\r\n   " +
                    "                     if ((bv[i >> 6] & (1L << (i & 0x3f)))==0)\r\n                " +
                    "        {\r\n                            destpayload[i].");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.OriginalName));
            this.Write(" = src_");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("[i];\r\n                        }\r\n                    }\r\n                ");
 } 
            this.Write("                ");
 } 
            this.Write("\r\n            ");
 } 
            this.Write("\r\n        }\r\n        }\r\n\r\n        ");
 foreach (var f in this.fields.Where(fld => fld.canBeFixed)) { 
            this.Write("\r\n        }\r\n        ");
 } 
            this.Write("\r\n        this.Observer.OnNext(resultBatch);\r\n\r\n        batch.Release();\r\n       " +
                    " batch.Return();\r\n    }\r\n\r\n    public override int CurrentlyBufferedOutputCount " +
                    "=> 0;\r\n\r\n    public override int CurrentlyBufferedInputCount => 0;\r\n}\r\n");
            return this.GenerationEnvironment.ToString();
        }
    }
}
