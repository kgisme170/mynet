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
    using System.Text;
    using System.Collections.Generic;
    using System;
    
    /// <summary>
    /// Class to produce the template output
    /// </summary>
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.VisualStudio.TextTemplating", "15.0.0.0")]
    internal partial class WhereTemplate : CommonUnaryTemplate
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
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;
using System.IO;
using System.Reflection;
using System.Runtime.Serialization;
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

  string TKey;
  var genericTypeNames = new string[]{"A", "B"};
  if (keyType.IsAnonymousType())
  {
    TKey = genericTypeNames[0];
  }
  else
  {
    genericTypeNames[0] = string.Empty;
    TKey= keyType.GetCSharpSourceSyntax();
  }

  string TPayload;
  if (payloadType.IsAnonymousType())
  {
    TPayload = genericTypeNames[1];
  }
  else
  {
    genericTypeNames[1] = string.Empty;
    TPayload = payloadType.GetCSharpSourceSyntax();
  }

  var genericParameters = Transformer.GenericParameterList(genericTypeNames);


            this.Write("\r\n[DataContract]\r\ninternal sealed class ");
            this.Write(this.ToStringHelper.ToStringWithCulture(className));
            this.Write(this.ToStringHelper.ToStringWithCulture(genericParameters));
            this.Write(" : UnaryPipe<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write(">\r\n{\r\n    private readonly MemoryPool<");
            this.Write(this.ToStringHelper.ToStringWithCulture(TKey));
            this.Write(", ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write("> pool;\r\n    private readonly Func<PlanNode, IQueryObject, PlanNode> queryPlanGen" +
                    "erator;\r\n\r\n    ");
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
            this.Write(@">();
        this.queryPlanGenerator = queryPlanGenerator;
    }

    public override void ProduceQueryPlan(PlanNode previous)
    {
        Observer.ProduceQueryPlan(queryPlanGenerator(previous, this));
    }

    public override int CurrentlyBufferedOutputCount => 0;

    public override int CurrentlyBufferedInputCount => 0;

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
            this.Write(";\r\n\r\n        var count = batch.Count;\r\n        batch.bitvector = batch.bitvector." +
                    "MakeWritable(this.pool.bitvectorPool);\r\n\r\n        ");
 if (!this.noTransformation) { 
            this.Write("        ");
 foreach (var f in this.fields) { 
            this.Write("\r\n        ");
 if (f.canBeFixed) { 
            this.Write("\r\n        fixed (");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.TypeName));
            this.Write("* ");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("_col = sourceBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col)\r\n        {\r\n\r\n        ");
 } else { 
            this.Write("\r\n        ");
 if (f.OptimizeString()) { 
            this.Write("        var ");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("_col = sourceBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(";\r\n        ");
 } else { 
            this.Write("        var ");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write("_col = sourceBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col;\r\n        ");
 } 
            this.Write("\r\n        ");
 } 
            this.Write("        ");
 } 
            this.Write("        ");
 } 
            this.Write("\r\n        ");
            this.Write(this.ToStringHelper.ToStringWithCulture(this.multiStringInit));
            this.Write("\r\n\r\n        ");
 if (this.vectorOperations != null) { 
            this.Write("\r\n        ");
            this.Write(this.ToStringHelper.ToStringWithCulture(this.vectorOperations));
            this.Write("\r\n\r\n        ");
 } else { 
            this.Write("\r\n        fixed (long* bv = batch.bitvector.col)\r\n        {\r\n            for (int" +
                    " i = 0; i < count; i++)\r\n            {\r\n                if ((bv[i >> 6] & (1L <<" +
                    " (i & 0x3f)))==0)\r\n                {\r\n\r\n                    ");
            this.Write(this.ToStringHelper.ToStringWithCulture(this.multiStringWrapperInit));
            this.Write("\r\n\r\n                    ");
 if (this.noTransformation) { 
            this.Write("                    var ");
            this.Write(this.ToStringHelper.ToStringWithCulture(PARAMETER));
            this.Write(" = new ");
            this.Write(this.ToStringHelper.ToStringWithCulture(TPayload));
            this.Write("();\r\n                    ");
 foreach (var f in this.fields) { 
            this.Write("\r\n                    ");
            this.Write(this.ToStringHelper.ToStringWithCulture(PARAMETER));
            this.Write(".");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.OriginalName));
            this.Write(" = sourceBatch.");
            this.Write(this.ToStringHelper.ToStringWithCulture(f.Name));
            this.Write(".col[i];\r\n                    ");
 } 
            this.Write("                    ");
 } 
            this.Write("\r\n                    if (!(");
            this.Write(this.ToStringHelper.ToStringWithCulture(PREDICATE));
            this.Write("))\r\n                    {\r\n                        bv[i >> 6] |= (1L << (i & 0x3f" +
                    "));\r\n                    }\r\n                }\r\n            }\r\n        }\r\n\r\n     " +
                    "   ");
 } // end if vectorized 
            this.Write("\r\n        ");
            this.Write(this.ToStringHelper.ToStringWithCulture(this.multiStringReturns));
            this.Write("\r\n\r\n        ");
 if (!this.noTransformation) { 
            this.Write("        ");
 foreach (var f in this.fields.Where(fld => fld.canBeFixed)) { 
            this.Write("\r\n        }\r\n        ");
 } 
            this.Write("        ");
 } 
            this.Write("\r\n        if (batch.RefreshCount() && (batch.Count == 0))\r\n            batch.Free" +
                    "();\r\n        else\r\n            this.Observer.OnNext(batch);\r\n    }\r\n}\r\n");
            return this.GenerationEnvironment.ToString();
        }
    }
}
