using System;
using System.Diagnostics;

namespace TraceConsole472
{
    class Program
    {
        static void Main()
        {
            ConsoleTraceListener myWriter = new ConsoleTraceListener();
            Trace.Listeners.Add(myWriter);
            //Trace.Listeners.Add(new TextWriterTraceListener(Console.Out));
            Trace.AutoFlush = true;
            Trace.Indent();
            Trace.WriteLine("Entering Main");
            Console.WriteLine("Hello World.");
            Trace.WriteLine("Exiting Main");
            Trace.TraceInformation("Test message.");
            // You must close or flush the trace to empty the output buffer.
            Trace.Flush();
            Trace.Unindent();
        }
    }
}
