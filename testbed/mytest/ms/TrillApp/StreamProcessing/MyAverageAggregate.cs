using Microsoft.StreamProcessing;
using Microsoft.StreamProcessing.Aggregates;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace StreamProcessing
{
    public struct AverageState
    {
        public long Sum;
        public ulong Count;
    }
    public class MyAverageAggregate : IAggregate<int, AverageState, double>
    {
        public Expression<Func<AverageState>> InitialState()
        {
            return () => new AverageState();
        }
        public Expression<Func<AverageState, long, int, AverageState>> Accumulate()
        {
            return (oldState, timestamp, input) => new AverageState
            { Count = oldState.Count + 1, Sum = oldState.Sum + input };
        }
        public Expression<Func<AverageState, long, int, AverageState>> Deaccumulate()
        {
            return (oldState, timestamp, input) => new AverageState
            { Count = oldState.Count - 1, Sum = oldState.Sum - input };
        }
        public Expression<Func<AverageState, AverageState, AverageState>> Difference()
        {
            return (left, right) => new AverageState
            { Count = left.Count - right.Count, Sum = left.Sum - right.Sum };
        }
        public Expression<Func<AverageState, double>> ComputeResult()
        {
            return state => (double)state.Sum / state.Count;
        }
    }

    public static class MyExtensions
    {
        public static IAggregate<TSource, AverageState, double> MyAverage<TKey, TSource>(
        this Window<TKey, TSource> window, Expression<Func<TSource, int>> selector)
        {
            var aggregate = new MyAverageAggregate();
            return aggregate.Wrap(selector);
        }
    }
    /*
    public static class MyExtensions
    {
        public static IAggregate<TSource, NullOutputWrapper<AverageState>, double?>
        MyAverage<TKey, TSource>(this Window<TKey, TSource> window,
        Expression<Func<TSource, int?>> selector)
        {
            var aggregate = new MyAverageAggregate();
            var aggregate1 = aggregate.MakeOutputNullableAndOutputNullsWhenEmpty();
            var aggregate2 = aggregate1.MakeInputNullableAndSkipNulls();
            return aggregate2.Wrap(selector);
        }
    }
    */
}
