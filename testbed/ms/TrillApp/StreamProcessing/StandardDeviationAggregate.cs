using Microsoft.StreamProcessing;
using Microsoft.StreamProcessing.Aggregates;
using System;
using System.Linq.Expressions;
using System.Reactive.Linq;

namespace StreamProcessing
{
    public struct StandardDeviationState
    {
        public ulong Count;
        public long Sum;
        public long SumSquared;
    }

    public class StandardDeviationAggregate : IAggregate<int, StandardDeviationState, double>
    {
        public Expression<Func<StandardDeviationState>> InitialState()
        {
            return () => new StandardDeviationState();
        }

        public Expression<Func<StandardDeviationState, long, int, StandardDeviationState>> Accumulate()
        {
            return (oldState, timestamp, input) => new StandardDeviationState
            {
                Count = oldState.Count + 1,
                Sum = oldState.Sum + input,
                SumSquared = oldState.SumSquared + ((long)input * input)
            };
        }

        public Expression<Func<StandardDeviationState, long, int, StandardDeviationState>> Deaccumulate()
        {
            return (oldState, timestamp, input) => new StandardDeviationState
            {
                Count = oldState.Count - 1,
                Sum = oldState.Sum - input,
                SumSquared = oldState.SumSquared - ((long)input * input)
            };
        }

        public Expression<Func<StandardDeviationState, StandardDeviationState, StandardDeviationState>> Difference()
        {
            return (left, right) => new StandardDeviationState
            {
                Count = left.Count - right.Count,
                Sum = left.Sum - right.Sum,
                SumSquared = left.SumSquared - right.SumSquared
            };
        }

        public Expression<Func<StandardDeviationState, double>> ComputeResult()
        {
            return state => Math.Sqrt(((double)state.SumSquared / state.Count) - ((double)(state.Sum * state.Sum) / (state.Count * state.Count)));
        }

        public static void Test()
        {
            StreamEvent<int>[] values = GetValues.Get();

            IObservable<StreamEvent<int>> ob = values.ToObservable();
            var input = ob.ToStreamable();
            Console.WriteLine("Streamable Input =");
            ob.ForEachAsync(e => Console.WriteLine(e)).Wait();
            Console.WriteLine();

            Console.WriteLine("StreamEventObservable Input =");
            input.ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();

            Console.WriteLine();
            var output = input.Where(r => r > 5);

            Console.WriteLine();
            Console.WriteLine("Output =");
            output.ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();

            var output2 = input.Aggregate(
                w => w.StandardDeviation(v => v),
                w => w.Count(),
                (std, count) => new { StandardDeviation = std, Count = count }
            );
            Console.WriteLine("Output2 =");
            output2.ToStreamEventObservable().ForEachAsync(e => Console.WriteLine(e)).Wait();
        }
    }

    public static class StandardDeviationExtensions
    {
        public static IAggregate<TSource, StandardDeviationState, double> StandardDeviation<TKey, TSource>(
            this Window<TKey, TSource> window, Expression<Func<TSource, int>> selector)
        {
            var aggregate = new StandardDeviationAggregate();
            return aggregate.Wrap(selector);
        }
    }
}