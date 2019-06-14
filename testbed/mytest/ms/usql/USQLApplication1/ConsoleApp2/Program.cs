using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;
using Microsoft.ComplexEventProcessing.Extensibility;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Threading;

namespace ConsoleApp2
{
    public class StandardDeviationUDA : CepAggregate<Payload, double>
    {
        /// <summary>
        /// Calculation.
        /// </summary>
        /// <param name="Payloads">Payloads</param>
        /// <returns></returns>
        public override double GenerateOutput(IEnumerable<Payload> Payloads)
        {
            // Calculate mean and count
            var sum = 0.0;
            var count = 0;
            foreach (var q in Payloads)
            {
                sum += q.Value;
                count++;
            }
            var mean = sum / count;

            // Add deviation squares
            sum = 0.0;
            foreach (var q in Payloads)
            {
                sum += (q.Value - mean) * (q.Value - mean);
            }

            // Calculate standard deviation
            var stddev = Math.Sqrt(sum / count);

            return stddev;
        }
    }

    public static class ExtensionMethods
    {
        /// <summary>
        /// Calculates the Standard Deviation
        /// </summary>
        /// <param name="window"></param>
        /// <returns></returns>
        [CepUserDefinedAggregate(typeof(StandardDeviationUDA))]
        public static double StandardDeviation(this CepWindow<Payload> window)
        {
            // This method is actually never executed. Instead StreamInsight 
            // invokes the StandardDeviationUDA class.

            // Throw an error if method is executed.
            throw CepUtility.DoNotCall();
        }
    }

    // 输入适配器，读取输入配置，并读入输入数据(Payload所指类型)
    public class InputAdapter : TypedPointInputAdapter<Payload>
    {
        public readonly static IFormatProvider QuoteFormatProvider = CultureInfo.InvariantCulture.NumberFormat;
        private PointEvent<Payload> pendingEvent; // 当前的event，用于处理重启任务的记录
        private InputConfig _config;
        private SortedList<DateTime, string[]> quotes; // 读入的数据
        private IEnumerator<KeyValuePair<DateTime, string[]>> quoteEnumerator; // IEnumerator是有状态的
        private SortedList<string, int> columns; // 读入csv的列名

        public InputAdapter(InputConfig config)
        {
            _config = config;

            var streamReader = new StreamReader(config.Filename);
            var line = streamReader.ReadLine();
            var values = line.Split(',');
            columns = new SortedList<string, int>(values.Length);
            for (int i = 0; i < values.Length; i++)
                columns.Add(values[i], i);

            quotes = new SortedList<DateTime, string[]>();
            while (!streamReader.EndOfStream)
            {
                line = streamReader.ReadLine();
                values = line.Split(',');
                var date = DateTime.Parse(values[0], QuoteFormatProvider);
                quotes.Add(date, values);
            }
            quoteEnumerator = quotes.GetEnumerator();

            streamReader.Close();
        }

        public override void Start()
        {
            ProduceEvents();
        }

        public override void Resume()
        {
            ProduceEvents();
        }

        protected override void Dispose(bool disposing)
        {
            base.Dispose(disposing);
        }

        /// <summary>
        /// Main loop
        /// </summary>
        private void ProduceEvents()
        {
            var currEvent = default(PointEvent<Payload>);

            EnqueueCtiEvent(new DateTimeOffset(_config.StartDate, TimeSpan.Zero));
            try
            {
                // Loop until stop signal
                while (AdapterState != AdapterState.Stopping)
                {
                    if (pendingEvent != null)
                    {
                        currEvent = pendingEvent;
                        pendingEvent = null;
                    }
                    else
                    {
                        if (quoteEnumerator.MoveNext())
                        {
                            try
                            {
                                var quote = quoteEnumerator.Current;
                                var date = new DateTimeOffset(quote.Key, TimeSpan.Zero);

                                foreach (var columnName in _config.ColumnNames)
                                {
                                    var i = columns[columnName];
                                    var value = double.Parse(quote.Value[i], QuoteFormatProvider);

                                    // Produce INSERT event
                                    currEvent = CreateInsertEvent();
                                    currEvent.StartTime = date;
                                    currEvent.Payload = new Payload
                                    {
                                        StockID = _config.ID,
                                        FieldID = columnName,
                                        Value = value
                                    };
                                    pendingEvent = null;
                                    //PrintEvent(currEvent);
                                    Enqueue(ref currEvent);
                                }

                                // Also send an CTI event
                                EnqueueCtiEvent(date);

                            }
                            catch
                            {
                                // Error handling should go here
                            }
                            Thread.Sleep(_config.Interval);
                        }
                        else
                        {
                            break;
                        }
                    }
                }

                if (pendingEvent != null)
                {
                    currEvent = pendingEvent;
                    pendingEvent = null;
                }

                PrepareToStop(currEvent);
                Stopped();
            }
            catch (AdapterException e)
            {
                Console.WriteLine("AdvantIQ.StockInsightTypedPointInput.ProduceEvents - " + e.Message + e.StackTrace);
            }
        }

        private void PrepareToStop(PointEvent<Payload> currEvent)
        {
            //EnqueueCtiEvent(DateTime.Now);
            if (currEvent != null)
            {
                // Do this to avoid memory leaks
                ReleaseEvent(ref currEvent);
            }
        }

        private void PrepareToResume(PointEvent<Payload> currEvent)
        {
            pendingEvent = currEvent;
        }

        private void PrintEvent(PointEvent<Payload> evt)
        {
            Console.WriteLine("Input: " + evt.EventKind + " " +
                evt.StartTime + " " + evt.Payload.StockID + " " +
                evt.Payload.FieldID + " " + evt.Payload.Value);
        }
    }

    public class PayloadTypedPointOutput : TypedPointOutputAdapter<Payload>
    {
        private EventWaitHandle _adapterStopSignal;

        public PayloadTypedPointOutput(PayloadOutputConfig config)
        {
            if (!string.IsNullOrEmpty(config.AdapterStopSignal))
                _adapterStopSignal = EventWaitHandle.OpenExisting(config.AdapterStopSignal);
            else
                _adapterStopSignal = null;
        }

        public override void Start()
        {
            ConsumeEvents();
        }

        public override void Resume()
        {
            ConsumeEvents();
        }

        protected override void Dispose(bool disposing)
        {
            base.Dispose(disposing);
        }

        /// <summary>
        /// Main loop
        /// </summary>
        private void ConsumeEvents()
        {
            PointEvent<Payload> currEvent;
            DequeueOperationResult result;

            try
            {
                // Run until stop state
                while (AdapterState != AdapterState.Stopping)
                {
                    result = Dequeue(out currEvent);

                    // Take a break if queue is empty
                    if (result == DequeueOperationResult.Empty)
                    {
                        PrepareToResume();
                        Ready();
                        return;
                    }
                    else
                    {
                        //PrintEvent(currEvent);

                        // Write to console
                        if (currEvent.EventKind == EventKind.Insert)
                        {
                            Console.WriteLine("Output: " +
                                currEvent.StartTime + " " +
                                currEvent.Payload.StockID + " " +
                                currEvent.Payload.FieldID + " " +
                                currEvent.Payload.Value.ToString("f2"));
                        }

                        ReleaseEvent(ref currEvent);
                    }
                }
                result = Dequeue(out currEvent);
                PrepareToStop(currEvent, result);
                Stopped();
            }
            catch (AdapterException e)
            {
                Console.WriteLine("AdvantIQ.StockInsightTypedPointOutput.ConsumeEvents - " + e.Message + e.StackTrace);
            }

            if (_adapterStopSignal != null)
                _adapterStopSignal.Set();
        }

        private void PrepareToResume()
        {
        }

        private void PrepareToStop(PointEvent<Payload> currEvent, DequeueOperationResult result)
        {
            if (result == DequeueOperationResult.Success)
            {
                ReleaseEvent(ref currEvent);
            }
        }

        private void PrintEvent(PointEvent<Payload> evt)
        {
            if (evt.EventKind == EventKind.Cti)
            {
                //Console.WriteLine("Output: CTI " + evt.StartTime);
            }
            else
            {
                Console.WriteLine("Output: " + evt.EventKind + " " +
                    evt.StartTime + " " + evt.Payload.StockID + " " +
                    evt.Payload.Value);
            }
        }
    }

    class Program
    {
        private static void RunQuery(CepStream<Payload> cepStream, Application application)
        {
            // Configure output adapter
            var outputConfig = new PayloadOutputConfig();

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

        static void Main(string[] args)
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
                    var inputAdapter = application.CreateInputAdapter<PayloadInputFactory>("PayloadInput", "Description...");
                    var outputAdapter = application.CreateOutputAdapter<PayloadOutputFactory>("PayloadOutput", "Description...");
                    queryBinder.BindProducer<Payload>("input", inputAdapter, ericSEKConfig, EventShape.Point);
                    queryBinder.AddConsumer<Payload>("output", outputAdapter, new PayloadOutputConfig(), EventShape.Point, StreamEventOrder.ChainOrdered);

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
