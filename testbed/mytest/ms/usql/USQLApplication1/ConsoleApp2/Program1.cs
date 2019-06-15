using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace ConsoleApp2
{
    public class TollData
    {
        public DateTime Timestamp { get; set; }
        public int Number { get; set; }
    }

    public class TollConfig
    {
        public string TollName { get; set; }
        public IEnumerator<TollData> TollData { get; set; }
    }

    public class OutConfig
    {
        public string OutConfigName { get; set; }
    }

    public class MediationData
    {
        public int Number { get; set; }
    }

    public class InAdapter : TypedPointInputAdapter<MediationData>
    {
        readonly TollConfig config;
        public InAdapter(TollConfig config)
        {
            this.config = config;
        }

        private void ProduceEvents()
        {
            EnqueueCtiEvent(new DateTimeOffset(DateTime.Now, TimeSpan.Zero));
            var data = config.TollData;

            while (data.MoveNext())
            {
                var e = CreateInsertEvent();
                TollData d = data.Current;
                e.StartTime = d.Timestamp;
                e.Payload = new MediationData
                {
                    Number = d.Number
                };
                Enqueue(ref e);
            }
        }

        // override
        public override void Resume()
        {
            ProduceEvents();
        }

        public override void Start()
        {
            ProduceEvents();
        }
    }

    class InputFactory : ITypedInputAdapterFactory<TollConfig>
    {
        public InputAdapterBase Create<TPayload>(TollConfig configInfo, EventShape eventShape)
        {
            return new InAdapter(configInfo);
        }

        public void Dispose()
        {
            Console.WriteLine("InputFactory dispose()");
        }
    }
    public class OutAdapter : TypedPointOutputAdapter<MediationData>
    {
        private void ConsumeEvents()
        {
            while (true)
            {
                var result = Dequeue(out PointEvent<MediationData> d);
                if(result == DequeueOperationResult.Empty)
                {
                    return;
                }
                if(d.EventKind == EventKind.Insert)
                {
                    Console.WriteLine("[" + d.StartTime + "]" + d.Payload.Number);
                }
            }
        }

        // implement
        public override void Resume()
        {
            ConsumeEvents();
        }

        public override void Start()
        {
            ConsumeEvents();
        }
    }

    public class OutputFactory : ITypedOutputAdapterFactory<TollConfig>
    {
        public OutputAdapterBase Create<TPayload>(TollConfig configInfo, EventShape eventShape)
        {
            throw new NotImplementedException();
        }

        public void Dispose()
        {
            Console.WriteLine("OutputFactory dispose()");
        }
    }

    partial class Program
    {
        static DateTime now = DateTime.Now;
        static int currentMinute = 1;
        public static TollData GetTollData(int n)
        {
            return new TollData
            {
                Timestamp = DateTime.Now + TimeSpan.FromMinutes(currentMinute++),
                Number = n
            };
        }
        public static IEnumerator<TollData> GetData()
        {
            return new List<TollData>()
            {
                GetTollData(2),
                GetTollData(4),
                GetTollData(6),
                GetTollData(8),
                GetTollData(10),
                GetTollData(12),
                GetTollData(18),
                GetTollData(0),
                GetTollData(0),
                GetTollData(3),
                GetTollData(3),
                GetTollData(3),
                GetTollData(6),
                GetTollData(9),
                GetTollData(0),
                GetTollData(0),
                GetTollData(0),
                GetTollData(15),
                GetTollData(9),
            }.GetEnumerator();
        }
        public static void Test1()
        {
            Server server = Server.Create("MyInstance");
            Application application = server.CreateApplication("Myapp");

            var tollData = GetData();
            var tollConfig = new TollConfig()
            {
                TollName = "Toll1",
                TollData = tollData
            };

            var input1 = CepStream<TollData>.Create("input1");
            var outConfig = new OutConfig()
            {
                OutConfigName = "outConfig1"
            };
            var filter = from e in input1
                         where e.Number > 3
                         select e;
            var binder = new QueryBinder(application.CreateQueryTemplate(
                "template1",
                "description1",
                input1));
            var inputAdaptor = application.CreateInputAdapter<InputFactory>("nameIn", "descIn");
            var outputAdaptor = application.CreateOutputAdapter<OutputFactory>("nameOut", "descOut");

            binder.BindProducer<TollData>(
                "input1",
                inputAdaptor,
                tollConfig,
                EventShape.Point);
            binder.AddConsumer<TollData>(
                "output1",
                outputAdaptor,
                outConfig,
                EventShape.Point,
                StreamEventOrder.ChainOrdered);

            var query = application.CreateQuery("queyName", "desc", binder);
            query.Start();
            DiagnosticView diagnosticView;
            do
            {
                Thread.Sleep(100);
                diagnosticView = query.Application.Server.GetDiagnosticView(query.Name);
            } while ((string)diagnosticView["QueryState"] == "Running");

            query.Stop();

            application.Delete();
            server.Dispose();
        }
    }
}
