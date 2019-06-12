using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.IO;
using System.Globalization;
using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;
using Microsoft.ComplexEventProcessing.Linq;

namespace ConsoleApp2
{
    //数据Payload
    public class StockQuote
    {
        public string StockID { get; set; }
        public string FieldID { get; set; }
        public double Value { get; set; }
    }

    //输入配置
    public class StockQuoteInputConfig
    {
        public string ID { get; set; }
        public string Filename { get; set; }
        public string[] ColumnNames { get; set; }
        public DateTime StartDate { get; set; }
        public int Interval { get; set; }
    }

    // 输入适配器，读取输入配置，并读入输入数据(Payload所指类型)
    public class StockQuoteTypedPointInput : TypedPointInputAdapter<StockQuote>
    {
        public readonly static IFormatProvider QuoteFormatProvider = CultureInfo.InvariantCulture.NumberFormat;
        private PointEvent<StockQuote> pendingEvent;
        private StockQuoteInputConfig _config;
        private SortedList<DateTime, string[]> quotes; // 读入的数据
        private IEnumerator<KeyValuePair<DateTime, string[]>> quoteEnumerator;
        private SortedList<string, int> columns; // 读入csv的列名

        public StockQuoteTypedPointInput(StockQuoteInputConfig config)
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
            var currEvent = default(PointEvent<StockQuote>);

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
                                    currEvent.Payload = new StockQuote
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

        private void PrepareToStop(PointEvent<StockQuote> currEvent)
        {
            //EnqueueCtiEvent(DateTime.Now);
            if (currEvent != null)
            {
                // Do this to avoid memory leaks
                ReleaseEvent(ref currEvent);
            }
        }

        private void PrepareToResume(PointEvent<StockQuote> currEvent)
        {
            pendingEvent = currEvent;
        }

        private void PrintEvent(PointEvent<StockQuote> evt)
        {
            Console.WriteLine("Input: " + evt.EventKind + " " +
                evt.StartTime + " " + evt.Payload.StockID + " " +
                evt.Payload.FieldID + " " + evt.Payload.Value);
        }
    }
    public class StockQuoteInputFactory : ITypedInputAdapterFactory<StockQuoteInputConfig>
    {
        public InputAdapterBase Create<TPayload>(StockQuoteInputConfig config, EventShape eventShape)
        {
            // Only support the point event model
            if (eventShape == EventShape.Point)
                return new StockQuoteTypedPointInput(config);
            else
                return default(InputAdapterBase);
        }

        public void Dispose()
        {
        }
    }

    public class StockQuoteOutputConfig
    {
        public string AdapterStopSignal { get; set; }
    }

    public class StockQuoteTypedPointOutput : TypedPointOutputAdapter<StockQuote>
    {
        private EventWaitHandle _adapterStopSignal;

        public StockQuoteTypedPointOutput(StockQuoteOutputConfig config)
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
            PointEvent<StockQuote> currEvent;
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

        private void PrepareToStop(PointEvent<StockQuote> currEvent, DequeueOperationResult result)
        {
            if (result == DequeueOperationResult.Success)
            {
                ReleaseEvent(ref currEvent);
            }
        }

        private void PrintEvent(PointEvent<StockQuote> evt)
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

    public class StockQuoteOutputFactory : ITypedOutputAdapterFactory<StockQuoteOutputConfig>
    {
        public OutputAdapterBase Create<TPayload>(StockQuoteOutputConfig config, EventShape eventShape)
        {
            // Only support the point event model
            if (eventShape == EventShape.Point)
                return new StockQuoteTypedPointOutput(config);
            else
                return default(OutputAdapterBase);
        }

        public void Dispose()
        {
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            using (Server server = Server.Create("MyInstance"))
            {
                try
                {
                    Application myApp = server.CreateApplication("MyApp");
                    var ericSEKConfig = new StockQuoteInputConfig
                    {
                        ID = "ERIC-SEK",
                        Filename = "eric_b_sek_2009.csv",
                        ColumnNames = new string[] { "Open", "High", "Low", "Close", "Volume", "Adj Close" },
                        StartDate = new DateTime(2009, 01, 01),
                        Interval = 0
                    };
                    var inputstream = CepStream<StockQuote>.Create("inputStream",
                                                                   typeof(StockQuoteInputFactory),
                                                                   ericSEKConfig,
                                                                   EventShape.Point);

                    var filtered = from e in inputstream
                                   where e.Value > 95
                                   select e;

                    var query = filtered.ToQuery(myApp,
                                                 "filterQuery",
                                                 "Filter out Values over 95",
                                                 typeof(StockQuoteOutputFactory),
                                                 new StockQuoteOutputConfig(),
                                                 EventShape.Point,
                                                 StreamEventOrder.FullyOrdered);

                    query.Start();
                    Console.ReadLine();
                    query.Stop();
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
        }
    }
}
