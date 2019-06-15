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

        public override string ToString()
        {
            return "TollData: Timestamp = " + Timestamp + ", Number = " + Number;
        }
    }

    public class InConfig
    {
        public string TollName { get; set; }
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
        readonly DateTime now = DateTime.Now;
        readonly InConfig config;
        int currentMinute = 1;
        IEnumerator<TollData> data;
        TollData GetTollData(int n)
        {
            return new TollData
            {
                Timestamp = DateTime.Now + TimeSpan.FromMinutes(currentMinute++),
                Number = n
            };
        }

        IEnumerator<TollData> GetData()
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

        public InAdapter(InConfig config)
        {
            this.config = config;
            data = GetData();
        }

        private void ProduceEvents()
        {
            var dateTimeOffset = new DateTimeOffset(new DateTime(2019, 06, 01), TimeSpan.Zero);
            EnqueueCtiEvent(dateTimeOffset);
            Console.WriteLine("Begin input adapter process event");
            DateTime date = DateTime.Now;
            while (data.MoveNext())
            {
                var e = CreateInsertEvent();
                TollData d = data.Current;
                date = d.Timestamp;
                e.StartTime = d.Timestamp;
                e.Payload = new MediationData
                {
                    Number = d.Number
                };
                Enqueue(ref e);
                Console.WriteLine("Data:" + d + " is enqueued");
            }
            EnqueueCtiEvent(new DateTimeOffset(new DateTime(2019, 06, 21), TimeSpan.Zero));
            Stopped();
            Console.WriteLine("=====End input adapter process event");
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

    class InputFactory : ITypedInputAdapterFactory<InConfig>
    {
        public InputAdapterBase Create<TPayload>(InConfig configInfo, EventShape eventShape)
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
        public OutAdapter(OutConfig config)
        {

        }
        private void ConsumeEvents()
        {
            Console.WriteLine("->Begin output adapter consume event");

            while (AdapterState != AdapterState.Stopping)
            {
                Console.WriteLine("->->Enter output while");
                var result = Dequeue(out PointEvent<MediationData> d);
                Console.WriteLine("->->After deque");
                if (result == DequeueOperationResult.Empty)
                {
                    Console.WriteLine("->->empty");
                    Ready(); // 进入下一次循环
                    return;
                }
                if(d.EventKind == EventKind.Insert)
                {
                    Console.WriteLine("->->[" + d.StartTime + "]" + d.Payload.Number);
                }
            }
            Stopped();
            Console.WriteLine("->End output adapter consume event");
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

    public class OutputFactory : ITypedOutputAdapterFactory<OutConfig>
    {
        public OutputAdapterBase Create<TPayload>(OutConfig configInfo, EventShape eventShape)
        {
            return new OutAdapter(configInfo);
        }

        public void Dispose()
        {
            Console.WriteLine("OutputFactory dispose()");
        }
    }

    partial class Program
    {
        public static void Test1()
        {
            Server server = Server.Create("MyInstance");
            Application application = server.CreateApplication("Myapp");

            var InConfig = new InConfig()
            {
                TollName = "Toll1",
            };

            var input1 = CepStream<MediationData>.Create("input1");
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
            var inputAdapter = application.CreateInputAdapter<InputFactory>("nameIn", "descIn");
            var outputAdapter = application.CreateOutputAdapter<OutputFactory>("nameOut", "descOut");

            binder.BindProducer<MediationData>(
                "input1",
                inputAdapter,
                InConfig,
                EventShape.Point);
            binder.AddConsumer<MediationData>(
                "output1",
                outputAdapter,
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
