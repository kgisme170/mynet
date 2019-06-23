using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;

namespace ConsoleApp2
{
    public class PayloadOutputFactory : ITypedOutputAdapterFactory<PayloadOutputConfig>
    {
        public OutputAdapterBase Create<TPayload>(PayloadOutputConfig config, EventShape eventShape)
        {
            // Only support the point event model
            if (eventShape == EventShape.Point)
                return new OutputAdapterFromFile(config);
            else
                return default(OutputAdapterBase);
        }

        public void Dispose()
        {
        }
    }
}