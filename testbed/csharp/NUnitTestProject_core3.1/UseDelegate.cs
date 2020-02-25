using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Text;

namespace NUnitTestProject_core
{
    class UseDelegate
    {
        class TrainSignal
        {
            public Action TrainsAreComing;
            public void HereComesTheTain()
            {
                TrainsAreComing();
            }
        }
        class Car
        {
            public Car(TrainSignal trainSignal)
            {
                trainSignal.TrainsAreComing += StopTheCar; // construct the list
            }
            void StopTheCar()
            {
                Console.WriteLine("Stop");
            }
        }
        [Test]
        public static void TestUseDelegate()
        {
            TrainSignal trainSignal = new TrainSignal();
            new Car(trainSignal);
            new Car(trainSignal);
            new Car(trainSignal);
            new Car(trainSignal);
            trainSignal.HereComesTheTain();
        }
    }
}
