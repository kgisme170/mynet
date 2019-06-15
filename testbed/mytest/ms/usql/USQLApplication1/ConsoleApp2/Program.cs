using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Diagnostics;
using System.Linq;
using System.Threading;

namespace ConsoleApp2
{
    partial class Program
    {
        readonly private static PayloadOutputConfig outputConfig = new PayloadOutputConfig();
        private static void RunQuery(CepStream<Payload> cepStream, Application application)
        {
            // Configure output adapter

            // Create query and bind to the output adapter
            var query = cepStream.ToQuery(application, Guid.NewGuid().ToString(), "description", typeof(PayloadOutputFactory), outputConfig, EventShape.Point, StreamEventOrder.ChainOrdered);

            // Start query
            query.Start();

            // Wait until query change state
            DiagnosticView diagnosticView;
            do
            {
                Thread.Sleep(100);
                diagnosticView = query.Application.Server.GetDiagnosticView(query.Name);
            } while ((string)diagnosticView[DiagnosticViewProperty.QueryState] == "Running");

            // Stop query
            query.Stop();
        }

        public static void Main(string [] args)
        {
            // Create a tracer to output information on the console.
            TraceListener tracer = new ConsoleTraceListener();
            using (Server server = Server.Create("MyInstance"))
            {
                try
                {
                    Application application = server.CreateApplication("application");
                    var ericSEKConfig = new InputConfig
                    {
                        ID = "ERIC-SEK",
                        Filename = "eric_b_sek_2009.csv",
                        ColumnNames = new string[] { "Open", "High", "Low", "Close", "Volume", "Adj Close" },
                        StartDate = new DateTime(2009, 01, 01),
                        Interval = 0
                    };

                    // Implicit 方式
                    var cepStream = CepStream<Payload>.Create("cepStream",
                                                                   typeof(PayloadInputFactory),
                                                                   ericSEKConfig,
                                                                   EventShape.Point);

                    var filtered = from e in cepStream
                                   where e.Value > 95
                                   select e;
                    /*
                    var query = filtered.ToQuery(application,
                                                 "filterQuery",
                                                 "Filter out Values over 95",
                                                 typeof(PayloadOutputFactory),
                                                 new PayloadOutputConfig(),
                                                 EventShape.Point,
                                                 StreamEventOrder.FullyOrdered);
                                                 */
                    RunQuery(filtered, application);

                    var avgCepStream = from w in cepStream.Where(e => e.FieldID == "Close")
                                                 .HoppingWindow(TimeSpan.FromDays(7), TimeSpan.FromDays(1), HoppingWindowOutputPolicy.ClipToWindowEnd)
                                       select new Payload()
                                       {
                                           StockID = "ERIC",
                                           FieldID = "7-day avg",
                                           Value = w.Avg(e => e.Value)
                                       };

                    RunQuery(avgCepStream, application);

                    var ericUSDGroupCepStream = from e in cepStream
                                                group e by e.FieldID into eGroup
                                                from w in eGroup.HoppingWindow(TimeSpan.FromDays(7), TimeSpan.FromDays(1), HoppingWindowOutputPolicy.ClipToWindowEnd)
                                                select new Payload()
                                                {
                                                    StockID = "ERIC 7-day avg",
                                                    FieldID = eGroup.Key,
                                                    Value = w.Avg(e => e.Value)
                                                };

                    RunQuery(ericUSDGroupCepStream, application);

                    var bigLooserCepStream = (from e1 in cepStream
                                              from e2 in cepStream.ShiftEventTime(e => e.StartTime.AddDays(7))
                                              where e1.FieldID == "Close" && e2.FieldID == "Close"
                                              select new Payload()
                                              {
                                                  StockID = "ERIC > 10% drop",
                                                  FieldID = "Close",
                                                  Value = (e1.Value - e2.Value) / e2.Value * 100
                                              }).Where(e => e.Value < -10);


                    RunQuery(bigLooserCepStream, application);

                    // Explicit 方式
                    var input = CepStream<Payload>.Create("input");
                    var stddevCepStream = from w in input.Where(e => e.FieldID == "Close")
                                                                     .HoppingWindow(TimeSpan.FromDays(7), TimeSpan.FromDays(1), HoppingWindowOutputPolicy.ClipToWindowEnd)
                                          select new Payload()
                                          {
                                              StockID = "ERIC",
                                              FieldID = "7-day Stddev",
                                              Value = w.StandardDeviation()
                                          };
                    var queryTemplate = application.CreateQueryTemplate("standardDeviationExampleTemplate", "Description...", stddevCepStream);

                    var queryBinder = new QueryBinder(queryTemplate);
                    var InputAdapterFromFile = application.CreateInputAdapter<PayloadInputFactory>("PayloadInput", "Description...");
                    var outputAdapter = application.CreateOutputAdapter<PayloadOutputFactory>("PayloadOutput", "Description...");
                    queryBinder.BindProducer<Payload>("input", InputAdapterFromFile, ericSEKConfig, EventShape.Point);
                    queryBinder.AddConsumer<Payload>("output", outputAdapter, outputConfig, EventShape.Point, StreamEventOrder.ChainOrdered);

                    var query = application.CreateQuery("standardDeviationExampleQuery", "Description...", queryBinder);
                    query.Start();

                    // Wait until output adapter signals that it is finished
                    DiagnosticView diagnosticView;
                    do
                    {
                        Thread.Sleep(100);
                        diagnosticView = query.Application.Server.GetDiagnosticView(query.Name);
                    } while ((string)diagnosticView["QueryState"] == "Running");

                    // Stop
                    query.Stop();
                    // Release resources
                    application.Delete();
                    server.Dispose();
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
        }
    }
}
