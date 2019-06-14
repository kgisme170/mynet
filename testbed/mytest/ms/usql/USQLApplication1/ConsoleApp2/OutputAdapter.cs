using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;
using System;
using System.Threading;

namespace ConsoleApp2
{
    public class OutputAdapter : TypedPointOutputAdapter<Payload>
    {
        private EventWaitHandle _adapterStopSignal;

        public OutputAdapter(PayloadOutputConfig config)
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
}