using Microsoft.ComplexEventProcessing.Extensibility;
using Microsoft.ComplexEventProcessing.Linq;
using System;
using System.Collections.Generic;

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
}