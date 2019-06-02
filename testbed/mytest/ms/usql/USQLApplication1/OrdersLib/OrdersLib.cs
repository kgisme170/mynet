using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
/*
using Microsoft.Analytics.Interfaces;
using Microsoft.Analytics.Interfaces;
using Microsoft.Analytics.Types.Sql;
*/
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
namespace MyNs
{
    public static class HelpersOut
    {
        public static string CustPrefix = "CUST_";
    }
    public class MyClass
    {
        public static class Helpers
        {
            public static string CustPrefix = "CUST_";
        }
        public static string Normalize(string s)
        {
            return s.ToLower();
        }
    }
}

/*
namespace MVA_UDAgg
{
    [SqlUserDefinedReducer(IsRecursive = true)]
    public class MySum : IAggregate<int, long>
    {
        long total;
        public override void Init() { total = 0; }
        public override void Accumulate(int value) { total += value; }
        public override long Terminate() { return total; }
    }
}
namespace Demo
{
    [SqlUserDefinedProcessor]
    public class HelloWorldProcessor : IProcessor
    {
        private string hw;
        public HelloWorldProcessor()
        {
            this.hw = System.IO.File.ReadAllText("helloworld.txt");
        }
        public override IRow Process(IRow input, IUpdatableRow output)
        {
            output.Set<int>("DepID", input.Get<int>("DepID"));
            output.Set<string>("DepName", input.Get<string>("DepName"));
            output.Set<string>("HelloWorld", hw);
            return output.AsReadOnly();
        }
    }
}
namespace MyUDTExamples
{
    public class BitFormatter : Microsoft.Analytics.Interfaces.IFormatter<Bits>
    {
        public BitFormatter()
        {
        }
        public void Serialize(
        Bits instance,
        IColumnWriter writer,
        ISerializationContext context)
        {
            using (var w = new System.IO.StreamWriter(writer.BaseStream))
            {
                var bitstring = instance.ToString();
                w.Write(bitstring);
                w.Flush();
            }
        }
        public Bits Deserialize(
        IColumnReader reader,
        ISerializationContext context)
        {
            using (var w = new System.IO.StreamReader(reader.BaseStream))
            {
                string bitstring = w.ReadToEnd();
                var bits = new Bits(bitstring);
                return bits;
            }
        }
    }
    [SqlUserDefinedType(typeof(BitFormatter))]
    public struct Bits
    {
        System.Collections.BitArray bitarray;
        public Bits(string s)
        {
            this.bitarray = new System.Collections.BitArray(s.Length);
            for (int i = 0; i < s.Length; i++)
            {
                this.bitarray[i] = (s[s.Length - i - 1] == '1' ? true : false);
            }
        }
        public int ToInteger()
        {
            int value = 0;
            for (int i = 0; i < this.bitarray.Length; i++)
            { if (bitarray[i]) { value += (int)System.Math.Pow(2, i); } }
            return value;
        }
        public override string ToString()
        {
            var sb = new System.Text.StringBuilder(this.bitarray.Length);
            for (int i = 0; i < this.bitarray.Length; i++)
            { sb.Append(this.bitarray[i] ? "1" : "0"); }
            return sb.ToString();
        }
    }
}
*/