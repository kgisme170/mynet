using Microsoft.ComplexEventProcessing;
using Microsoft.ComplexEventProcessing.Adapters;

namespace ConsoleApp2
{
    class PayloadInputFactory : ITypedInputAdapterFactory<InputConfig>
    {
        public InputAdapterBase Create<TPayload>(InputConfig config, EventShape eventShape)
        {
            // Only support the point event model
            if (eventShape == EventShape.Point)
                return new InputAdapterFromFile(config);
            else
                return default(InputAdapterBase);
        }

        public void Dispose()
        {
        }
    }
}