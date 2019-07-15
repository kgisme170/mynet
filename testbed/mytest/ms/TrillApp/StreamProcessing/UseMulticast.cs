using Microsoft.StreamProcessing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reactive.Linq;
namespace StreamProcessing
{
    class UseMulticast
    {
        private static IObservable<T> ToObservableInterval<T>(IEnumerable<T> source, TimeSpan period)
        {
            return Observable.Using(
                source.GetEnumerator,
                it => Observable.Generate(
                    default(object),
                    _ => it.MoveNext(),
                    _ => _,
                    _ =>
                    {
                        Console.WriteLine("Input {0}", it.Current);
                        return it.Current;
                    },
                    _ => period));
        }
        public class SensorReading
        {
            public int Time { get; set; }
            public int Value { get; set; }
            public override string ToString()
            {
                return new { Time, Value }.ToString();
            }
            public override bool Equals(object obj)
            {
                var other = obj as SensorReading;
                return other != null && Time == other.Time && Value == other.Value;
            }
            public override int GetHashCode()
            {
                return Time.GetHashCode() ^ Value.GetHashCode();
            }
        }
        private static IStreamable<Empty, SensorReading> CreateStream()
        {
            return ToObservableInterval(
                new[]
                {
                    new SensorReading { Time = 1, Value = 0 },
                    new SensorReading { Time = 2, Value = 20 },
                    new SensorReading { Time = 3, Value = 15 },
                    new SensorReading { Time = 4, Value = 30 },
                    new SensorReading { Time = 5, Value = 45 }, // here we crossed the threshold upward
                    new SensorReading { Time = 6, Value = 50 },
                    new SensorReading { Time = 7, Value = 30 }, // here we crossed downward // **** note that the current query logic only detects upward swings. ****/
                    new SensorReading { Time = 8, Value = 35 },
                    new SensorReading { Time = 9, Value = 60 }, // here we crossed upward again
                    new SensorReading { Time = 10, Value = 20 }
                },
                TimeSpan.FromMilliseconds(1000))
            .Select(r => StreamEvent.CreateInterval(r.Time, r.Time + 1, r))
            .ToStreamable();
        }
        public static void Test()
        {
            IStreamable<Empty, SensorReading> inputStream = CreateStream();
            const int threshold = 42;

            var crossedThreshold = inputStream.Multicast(
                input =>
                {
                    // Alter all events 1 sec in the future.
                    var alteredForward = input.AlterEventLifetime(s => s + 1, 1);

                    // Compare each event that occurs at input with the previous event.
                    // Note that, this one works for strictly ordered, strictly (e.g 1 sec) regular streams.
                    var filteredInputStream = input.Where(s => s.Value > threshold);
                    var filteredAlteredStream = alteredForward.Where(s => s.Value < threshold);
                    return filteredInputStream.Join(
                        filteredAlteredStream, (evt, prev) => new { evt.Time, Low = prev.Value, High = evt.Value });
                });

            crossedThreshold.ToStreamEventObservable().ForEachAsync(r => Console.WriteLine(r)).Wait();
        }
    }
}
