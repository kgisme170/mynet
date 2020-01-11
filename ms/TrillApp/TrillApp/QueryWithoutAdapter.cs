namespace TrillApp
{
    using Microsoft.ComplexEventProcessing;
    using Microsoft.ComplexEventProcessing.Extensibility;
    using Microsoft.ComplexEventProcessing.Linq;
    using System;
    using System.Collections.Generic;
    using System.Globalization;
    using System.Linq;
    using System.Linq.Expressions;
    using System.Reflection;
    using System.Text;
    using System.Timers;
    public class GeneratedEvent
    {
        /// <summary>
        /// Gets or sets the simulated measurement value.
        /// </summary>
        public string DeviceId { get; private set; }
        public float Value { get; private set; }

        public GeneratedEvent()
        {
            Random rnd = new Random();
            const int DeviceCount = 5;  // Cannot be passed through ctor of TPlayload
            const int MaxValue = 100;

            DeviceId = rnd.Next((int)DeviceCount).ToString();
            Value = (float)rnd.Next((int)MaxValue);
        }

        public override string ToString()
        {
            return DeviceId;
        }
    }

    /// <summary>
    /// Output type of the primary query.
    /// Dynamic query composition requires explicit types at the query
    /// boundaries.
    /// </summary>
    public class AnnotatedValue
    {
        public string DeviceID { get; set; }
        public float Value { get; set; }
        public float ValueDelta { get; set; }
    }

    /// <summary>
    /// Output type of the secondary query.
    /// Dynamic query composition requires explicit types at the query
    /// boundaries.
    /// </summary>
    public class MaxValue
    {
        public float Value { get; set; }
    }

    /// <summary>
    /// State of one device. Contains the device data and the corresponding timestamp.
    /// </summary>
    public class DeviceState<TPayload>
    {
        /// <summary>
        /// Stored payload.
        /// </summary>
        private TPayload data;

        /// <summary>
        /// Stored timestamp.
        /// </summary>
        private DateTimeOffset timestamp;

        /// <summary>
        /// Initializes a new instance of the DeviceState class.
        /// </summary>
        /// <param name="data">Payload to store.</param>
        /// <param name="timestamp">Timestamp to store.</param>
        public DeviceState(TPayload data, DateTimeOffset timestamp)
        {
            this.data = data;
            this.timestamp = timestamp;
        }

        /// <summary>
        /// Gets the payload of the stored event.
        /// </summary>
        public TPayload Data
        {
            get { return this.data; }
        }

        /// <summary>
        /// Gets the timestamp of the stored event.
        /// </summary>
        public DateTimeOffset Timestamp
        {
            get { return this.timestamp; }
        }
    }

    /// <summary>
    /// Base class for Tracer, which is an IObserver of TypedEvent.
    /// Derived classes must implement function
    ///     protected abstract void OnNextEvent(TEvent currentEvent) 
    /// to specify how to handle different events.
    /// </summary>
    abstract class TracerBase<T, TEvent> : IObserver<TEvent> where TEvent : TypedEvent<T>
    {
        private PropertyInfo[] _properties;
        private readonly bool _displayCti;
        private readonly bool _singleLine;
        private readonly string _traceName;

        public bool DisplayCti { get { return _displayCti; } }

        protected TracerBase(string traceName, bool displayCti, bool singleLine)
        {
            _traceName = traceName;
            _displayCti = displayCti;
            _singleLine = singleLine;
        }

        protected void AppendPayload(ref StringBuilder builder, object payload)
        {
            if (_properties == null)
                _properties = payload.GetType().GetProperties();

            foreach (PropertyInfo p in _properties)
            {
                object value = p.GetValue(payload, null);

                if (_singleLine)
                {
                    builder.AppendFormat(CultureInfo.InvariantCulture, " {0}", value);
                }
                else
                {
                    builder.AppendFormat("\t{0} = {1}", p.Name, value);
                }
            }
        }

        public void OnCompleted()
        {
        }

        public void OnError(Exception error)
        {
        }

        public void OnNext(TEvent value)
        {
            OnNextEvent(value);
        }

        protected abstract void OnNextEvent(TEvent currentEvent);
    }

    /// <summary>
    /// Point Event Tracer
    /// </summary>
    class TracerPoint<T> : TracerBase<T, PointEvent<T>>
    {
        public TracerPoint(string traceName, bool displayCti, bool singleLine)
            : base(traceName, displayCti, singleLine)
        { }

        protected override void OnNextEvent(PointEvent<T> currentEvent)
        {
            if (currentEvent.EventKind == EventKind.Insert)
            {
                StringBuilder builder = new StringBuilder()
                .Append("Point at ")
                .Append(currentEvent.StartTime.DateTime.ToShortTimeString());
                AppendPayload(ref builder, currentEvent.Payload);

                Console.WriteLine(builder.ToString());
            }
            else if (currentEvent.EventKind == EventKind.Cti)
            {
                if (DisplayCti)
                {
                    Console.WriteLine(
                        String.Format(CultureInfo.InvariantCulture, "CTI at {1}", currentEvent.StartTime));
                }
            }
        }
    }

    /// <summary>
    /// Interval Event Tracer
    /// </summary>
    class TracerInterval<T> : TracerBase<T, IntervalEvent<T>>
    {
        public TracerInterval(string traceName, bool displayCti, bool singleLine)
            : base(traceName, displayCti, singleLine)
        { }

        protected override void OnNextEvent(IntervalEvent<T> currentEvent)
        {
            if (currentEvent.EventKind == EventKind.Insert)
            {
                StringBuilder builder = new StringBuilder()
                .Append("Interval from ")
                .Append(currentEvent.StartTime.DateTime.ToShortTimeString())
                .Append(" to ")
                .Append(currentEvent.EndTime);
                AppendPayload(ref builder, currentEvent.Payload); ;

                Console.WriteLine(builder.ToString());
            }
            else if (currentEvent.EventKind == EventKind.Cti)
            {
                if (DisplayCti)
                {
                    Console.WriteLine(
                        String.Format(CultureInfo.InvariantCulture, "CTI at {1}", currentEvent.StartTime));
                }
            }
        }
    }

    /// <summary>
    /// Edge Event Tracer
    /// </summary>
    class TracerEdge<T> : TracerBase<T, EdgeEvent<T>>
    {
        public TracerEdge(string traceName, bool displayCti, bool singleLine)
            : base(traceName, displayCti, singleLine)
        { }

        protected override void OnNextEvent(EdgeEvent<T> currentEvent)
        {
            if (currentEvent.EventKind == EventKind.Insert)
            {
                StringBuilder builder = new StringBuilder();

                if (currentEvent.EdgeType == EdgeType.Start)
                {
                    builder
                        .Append("Edge start at :").Append(currentEvent.StartTime);
                }
                else
                {
                    builder
                        .Append(" Edge end at ")
                        .Append(currentEvent.EndTime)
                        .Append(" Edge start at :")
                        .Append(currentEvent.StartTime);
                }

                AppendPayload(ref builder, currentEvent.Payload); ;

                Console.WriteLine(builder.ToString());
            }
            else if (currentEvent.EventKind == EventKind.Cti)
            {
                if (DisplayCti)
                {
                    Console.WriteLine(
                        String.Format(CultureInfo.InvariantCulture, "CTI at {1}", currentEvent.StartTime));
                }
            }
        }
    }

    /// <summary>
    /// A UDA to compute the delta on a given event field between the first
    /// and the last event in the window.
    /// </summary>
    public class DeltaUda : CepTimeSensitiveAggregate<float, float>
    {
        /// <summary>
        /// Computes the aggregation over a window.
        /// </summary>
        /// <param name="events">Set of events contained in the window.</param>
        /// <param name="windowDescriptor">Window definition.</param>
        /// <returns>Aggregation result.</returns>
        public override float GenerateOutput(IEnumerable<IntervalEvent<float>> events, WindowDescriptor windowDescriptor)
        {
            // Make sure that the events are ordered in time.
            var orderedEvent = events.OrderBy(e => e.StartTime);

            return orderedEvent.Last().Payload - orderedEvent.First().Payload;
        }
    }

    /// <summary>
    /// Extension methods to expose the UDAs/UDOs in LINQ.
    /// </summary>
    public static class UdaExtensionMethods
    {
        /// <summary>
        /// Extension method for the UDA.
        /// </summary>
        /// <typeparam name="T">Payload type of the stream.</typeparam>
        /// <param name="window">Window to be passed to the UDA</param>
        /// <param name="map">Mapping from the payload to a float field in the payload.</param>
        /// <returns>Aggregation result.</returns>
        [CepUserDefinedAggregate(typeof(DeltaUda))]
        public static float Delta<T>(this CepWindow<T> window, Expression<Func<T, float>> map)
        {
            throw CepUtility.DoNotCall();
        }
    }

    class QueryWithoutAdapter
    {
    }
    public abstract class GeneratorBase<T, TEvent> : IObservable<TEvent> where TEvent : TypedEvent<T>
    {
        private readonly List<IObserver<TEvent>> eventObservers;
        protected readonly int EventInterval;
        protected readonly int EventIntervalDiff;
        protected readonly Random Rnd;

        protected GeneratorBase(int eventInterval, int eventIntervalDiff)
        {
            this.EventInterval = eventInterval;
            this.EventIntervalDiff = eventIntervalDiff;

            this.eventObservers = new List<IObserver<TEvent>>();
            this.Rnd = new Random();

            OnTimedEvent(null, null);
        }

        private void OnTimedEvent(object source, ElapsedEventArgs args)
        {
            foreach (var eventObserver in eventObservers)
            {
                TEvent e;
                PopulateEvent(out e);
                if (e == null)
                {
                    eventObserver.OnError(new Exception("Event is null"));
                }
                else
                {
                    eventObserver.OnNext(e);
                }
            }
            var timerInterval = Math.Max(EventInterval - (this.Rnd.Next(EventIntervalDiff * 2) - EventIntervalDiff), 1);
            var timer = new Timer(timerInterval);
            timer.Elapsed += OnTimedEvent;
            timer.AutoReset = false;
            timer.Enabled = true;
        }

        protected abstract void PopulateEvent(out TEvent typedEvent);

        public IDisposable Subscribe(IObserver<TEvent> eventObserver)
        {
            if (!eventObservers.Contains(eventObserver))
            {
                eventObservers.Add(eventObserver);
            }
            return new Unsubscriber(eventObservers, eventObserver);
        }

        private class Unsubscriber : IDisposable
        {
            private List<IObserver<TEvent>> observers;
            private IObserver<TEvent> observer;

            public Unsubscriber(List<IObserver<TEvent>> observers, IObserver<TEvent> observer)
            {
                this.observers = observers;
                this.observer = observer;
            }

            public void Dispose()
            {
                if (this.observer != null && observers.Contains(observer))
                {
                    observers.Remove(observer);
                }
            }
        }
    }

    /// <summary>
    /// Point event generator
    /// </summary>
    /// <typeparam name="T">type</typeparam>
    public class PointGenerator<T> : GeneratorBase<T, PointEvent<T>>
        where T : new()
    {
        public PointGenerator(int eventInterval, int eventIntervalVariance)
            : base(eventInterval, eventIntervalVariance)
        { }

        public static IQStreamable<T> GetStreamable(Application app, int cti, int interval, int variance)
        {
            var ats = new AdvanceTimeSettings(new AdvanceTimeGenerationSettings((uint)cti, TimeSpan.FromTicks(-1)), null, AdvanceTimePolicy.Drop);
            return app.DefineObservable(() => new PointGenerator<T>(interval, variance)).ToPointStreamable(e => e, ats);
        }

        protected override void PopulateEvent(out PointEvent<T> typedEvent)
        {
            typedEvent = PointEvent.CreateInsert(DateTime.Now, new T());
        }
    }


    /// <summary>
    /// Interval event generator
    /// </summary>
    /// <typeparam name="T">type</typeparam>
    public class IntervalGenerator<T> : GeneratorBase<T, IntervalEvent<T>>
        where T : new()
    {
        public IntervalGenerator(int eventInterval, int eventIntervalVariance)
            : base(eventInterval, eventIntervalVariance)
        { }

        public static IQStreamable<T> GetStreamable(Application app, int cti, int interval, int variance)
        {
            var ats = new AdvanceTimeSettings(new AdvanceTimeGenerationSettings((uint)cti, TimeSpan.FromTicks(-1)), null, AdvanceTimePolicy.Drop);
            return app.DefineObservable(() => new IntervalGenerator<T>(interval, variance)).ToIntervalStreamable(e => e, ats);
        }

        protected override void PopulateEvent(out IntervalEvent<T> typedEvent)
        {
            typedEvent = IntervalEvent.CreateInsert(DateTime.Now,
                                                    DateTime.Now +
                                                    TimeSpan.FromMilliseconds(
                                                        Math.Max(
                                                            EventInterval -
                                                            (this.Rnd.Next(EventIntervalDiff * 2) - EventIntervalDiff), 1)),
                                                    new T());
        }
    }

    /// <summary>
    /// Edge event generator
    /// </summary>
    /// <typeparam name="T">type</typeparam>
    public class EdgeGenerator<T> : GeneratorBase<T, EdgeEvent<T>>
        where T : new()
    {
        private Dictionary<string, DeviceState<T>> dataSourceState = new Dictionary<string, DeviceState<T>>();

        public EdgeGenerator(int eventInterval, int eventIntervalVariance)
            : base(eventInterval, eventIntervalVariance)
        { }

        public static IQStreamable<T> GetStreamable(Application app, int cti, int interval, int variance)
        {
            var ats = new AdvanceTimeSettings(new AdvanceTimeGenerationSettings((uint)cti, TimeSpan.FromTicks(-1)), null, AdvanceTimePolicy.Drop);
            return app.DefineObservable(() => new EdgeGenerator<T>(interval, variance)).ToEdgeStreamable(e => e, ats);
        }

        protected override void PopulateEvent(out EdgeEvent<T> typedEvent)
        {
            var t = new T();
            if (this.dataSourceState.ContainsKey(t.ToString()))
            {
                typedEvent = EdgeEvent<T>.CreateEnd(this.dataSourceState[t.ToString()].Timestamp, DateTime.Now, t);
                if (typedEvent == null)
                {
                    throw new Exception("Cannot create EdgeEvent End");
                }
                this.dataSourceState.Remove(t.ToString());
            }
            else
            {
                typedEvent = EdgeEvent<T>.CreateStart(DateTime.Now, t);
                if (typedEvent == null)
                {
                    throw new Exception("Cannot create EdgeEvent Start");
                }
                this.dataSourceState.Add(t.ToString(), new DeviceState<T>(t, DateTime.Now));
            }
        }
    }

    public class QueryWithoutAdpter
    {
        public static void Main(String[] args)
        {
            // Creating an embedded server.
            //
            // NOTE: replace "Default" with the instance name you provided
            // during StreamInsight setup.
            using (Server server = Server.Create("MyInstance"))
            {
                // Comment out if you want to create a service host and expose
                // the server's endpoint:

                //ServiceHost host = new ServiceHost(server.CreateManagementService());

                //host.AddServiceEndpoint(
                //    typeof(IManagementService),
                //    new WSHttpBinding(SecurityMode.Message),
                //    "http://localhost/MyStreamInsightServer");

                // To enable remote connection / debugging, you also need to uncomment the
                // lines "host.Open()" and "host.Close()".
                // In addition, the URI needs to be provisioned for the
                // account that runs this process (unless Administrator). To do this, open
                // an admin shell and execute the following command, using the respective
                // user credentials:
                // netsh http add urlacl url=http://+:80/MyStreamInsightServer user=<domain\username>

                //host.Open();
                try
                {
                    Application myApp = server.CreateApplication("DeviceReadings");

                    // Generate input point event observable.
                    var inputPointObservable = PointGenerator<GeneratedEvent>.GetStreamable(myApp, 1, 500, 450);

                    // Annotate the original values with the delta events by joining them.
                    // The aggregate over the count window produces a point event at
                    // the end of the window, which coincides with the second event in
                    // the window, so that they can be joined.
                    var annotatedValues = inputPointObservable.Multicast(inp =>
                        from left in inp
                        join right in
                            (from e in inp
                             group e by e.DeviceId into eachGroup
                             from win in eachGroup.CountWindow(2)
                             select new { ValueDelta = win.Delta(e => e.Value), SourceID = eachGroup.Key }
                             )
                        on left.DeviceId equals right.SourceID
                        select new AnnotatedValue() { DeviceID = left.DeviceId, Value = left.Value, ValueDelta = right.ValueDelta });

                    // Specify the secondary query logic: select only a specific sensor.
                    var filtered = from e in annotatedValues
                                   where e.DeviceID == "0"
                                   select e;

                    // Find the maximum of all sensor values within 5 second windows -
                    // provided the window contains one or more events.
                    var maxDelta = from win in filtered.TumblingWindow(TimeSpan.FromSeconds(5))
                                   select new MaxValue() { Value = win.Max(e => e.ValueDelta) };

                    // Point Observable for annotatedValues.
                    var annotatedValuesObservable = annotatedValues.ToPointObservable();

                    // Interval Observable for maxDelta.
                    var maxDeltaObservable = maxDelta.ToIntervalObservable();

                    // Turn annotatedValues into an observable of point events.
                    var annotatedValuesObserver = myApp.DefineObserver(() => new TracerPoint<AnnotatedValue>("", false, false));

                    // Observer for maxDelta.
                    var maxDeltaObserver = myApp.DefineObserver(() => new TracerInterval<MaxValue>("\nMaxDeltas\n", false, false));

                    using (annotatedValuesObservable.Bind(annotatedValuesObserver).With(maxDeltaObservable.Bind(maxDeltaObserver)).Run())
                    {
                        // Wait for key stroke to end.
                        Console.WriteLine("Press enter to stop at any time.");
                        Console.ReadLine();
                    }
                    Console.WriteLine("Process stopped. Press enter to exit application.");
                    Console.ReadLine();
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    Console.ReadLine();
                }
            }
        }
    }
}
