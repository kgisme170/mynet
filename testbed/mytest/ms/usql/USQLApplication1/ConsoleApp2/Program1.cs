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
        public string Direction { get; set; }
        public double Number { get; set; }

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
        public double Number { get; set; }
        public string Direction { get; set; }

        public override string ToString()
        {
            return "TollData: Direction = " + Direction + ", Number = " + Number;
        }
    }

    public class InAdapter : TypedPointInputAdapter<MediationData>
    {
        readonly DateTime now = new DateTime(2019, 06, 01);
        readonly InConfig config;
        int currentMinute = 1;
        IEnumerator<TollData> data;
        TollData GetTollData(int n, string g)
        {
            return new TollData
            {
                Timestamp = now + TimeSpan.FromMinutes(currentMinute++),
                Direction = g,
                Number = n
            };
        }

        IEnumerator<TollData> GetData()
        {
            return new List<TollData>()
            {
                GetTollData(2, "Up"),
                GetTollData(4, "Down"),

                GetTollData(6, "Up"),
                GetTollData(8, "Down"),
                GetTollData(10, "Down"),

                GetTollData(12, "Up"),
                GetTollData(18, "Up"),
                GetTollData(0, "Down"),

                GetTollData(0, "Up"),
                GetTollData(3, "Up"),
                GetTollData(3, "Up"),

                GetTollData(3, "Down"),
                GetTollData(6, "Down"),
                GetTollData(9, "Down"),

                GetTollData(0, "Up"),
                GetTollData(0, "Up"),
                GetTollData(0, "Up"),

                GetTollData(15, "Down"),
                GetTollData(9, "Up"),
            }.GetEnumerator();
        }

        public InAdapter(InConfig config)
        {
            this.config = config;
            data = GetData();
        }

        private void ProduceEvents()
        {
            var dateTimeOffset = new DateTimeOffset(new DateTime(2019, 06, 01), TimeSpan.FromHours(8));
            EnqueueCtiEvent(dateTimeOffset); // 把这个dateTimeOffset 当成是UTC时间
            Console.WriteLine("Begin input adapter process event");

            var e = default(PointEvent<MediationData>);
            while (AdapterState != AdapterState.Stopping)
            {
                if (data.MoveNext())
                {
                    e = CreateInsertEvent();
                    TollData d = data.Current;
                    var date = d.Timestamp;
                    e.StartTime = d.Timestamp; // e.StartTime 的 utc时间 enqueue
                    e.Payload = new MediationData
                    {
                        Direction = d.Direction,
                        Number = d.Number
                    };
                    Enqueue(ref e);
                    var date2 = new DateTimeOffset(date, TimeSpan.FromHours(8));
                    EnqueueCtiEvent(date2);
                    Console.WriteLine("Data:" + d + " is enqueued");
                }
                else
                {
                    break;
                }
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
                //Console.WriteLine("->->Enter output while");
                var result = Dequeue(out PointEvent<MediationData> d);
                //Console.WriteLine("->->After deque");
                if (result == DequeueOperationResult.Empty)
                {
                    //Console.WriteLine("->->empty");
                    PrepareToResume();
                    Ready(); // 进入下一次 query.Start 通知循环
                    return;
                }
                if(d.EventKind == EventKind.Insert)
                {
                    Console.WriteLine("->->[" + d.StartTime + "]" + d.Payload);
                }
                ReleaseEvent(ref d);
            }
            var ret = Dequeue(out PointEvent<MediationData> currEvent);
            PrepareToStop(currEvent, ret);
            Stopped();
            Console.WriteLine("->End output adapter consume event");
        }
        private void PrepareToResume()
        {
        }

        private void PrepareToStop(PointEvent<MediationData> currEvent, DequeueOperationResult result)
        {
            if (result == DequeueOperationResult.Success)
            {
                ReleaseEvent(ref currEvent);
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
        static Server server = Server.Create("MyInstance");
        static Application application = server.CreateApplication("Myapp");
        static InConfig inConfig = new InConfig()
        {
            TollName = "Toll1",
        };
        static OutConfig outConfig = new OutConfig()
        {
            OutConfigName = "outConfig1"
        };
        static InputAdapter inputAdapter = application.CreateInputAdapter<InputFactory>("nameIn", "descIn");
        static OutputAdapter outputAdapter = application.CreateOutputAdapter<OutputFactory>("nameOut", "descOut");

        public static void RunCepStream(CepStream<MediationData> cepStream)
        {
            var binder = new QueryBinder(application.CreateQueryTemplate(
                Guid.NewGuid().ToString(),
                "description1",
                cepStream));

            binder.BindProducer<MediationData>(
                "cepStream1",
                inputAdapter,
                inConfig,
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
            query.Delete();
        }

        public static void Test1()
        {
            var cepStream = CepStream<MediationData>.Create("cepStream1");

            Console.WriteLine("\n-----------------------------Filter e.Number > 3\n");
            var filter = from e in cepStream
                         where e.Number > 3
                         select e;
            RunCepStream(filter);

            Console.WriteLine("\n-----------------------------Get average for HoppingWindow(3,1)\n");
            var avgCepStream = from w in cepStream.Where(e=>true).HoppingWindow(TimeSpan.FromMinutes(3), TimeSpan.FromMinutes(1), HoppingWindowOutputPolicy.ClipToWindowEnd)
                               select new MediationData()
                               {
                                   Direction = "Average",
                                   Number = w.Avg(e => e.Number)
                               };
            RunCepStream(avgCepStream);

            Console.WriteLine("\n-----------------------------Sum of TumblingWindow(3)\n");
            var count = from win in cepStream.TumblingWindow(TimeSpan.FromMinutes(3))
                        select new MediationData()
                        {
                            Direction = "Average",
                            Number = win.Sum(e => e.Number)
                        };
            RunCepStream(count);

            Console.WriteLine("\n-----------------------------Group e.Direction With HoppingWindow(3,3)\n");
            var groupCepStream = from e in cepStream
                                 group e by e.Direction into eGroup
                                 from w in eGroup.HoppingWindow(TimeSpan.FromMinutes(3), TimeSpan.FromMinutes(3), HoppingWindowOutputPolicy.ClipToWindowEnd)
                                 select new MediationData()
                                 {
                                     Direction = eGroup.Key,
                                     Number = w.Avg(e => e.Number)
                                 };
            RunCepStream(groupCepStream);

            Console.WriteLine("\n-----------------------------ShiftEvent by 7 min\n");
            var shiftEventCepStream =  from e in cepStream.ShiftEventTime(e => e.StartTime.AddMinutes(7))
                                      select e;
            RunCepStream(shiftEventCepStream);

            application.Delete();
            server.Dispose();
        }
    }
}
