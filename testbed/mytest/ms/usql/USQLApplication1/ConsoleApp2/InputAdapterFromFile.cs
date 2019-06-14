using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Threading;

namespace ConsoleApp2
{
    public class InputAdapterFromFile : TypedPointInputAdapter<Payload>
    {
        public readonly static IFormatProvider QuoteFormatProvider = CultureInfo.InvariantCulture.NumberFormat;
        private PointEvent<Payload> pendingEvent; // 当前的event，用于处理重启任务的记录
        private InputConfig _config;
        private SortedList<DateTime, string[]> quotes; // 读入的数据
        private IEnumerator<KeyValuePair<DateTime, string[]>> quoteEnumerator; // IEnumerator是有状态的
        private SortedList<string, int> columns; // 读入csv的列名

        public InputAdapterFromFile(InputConfig config)
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
}