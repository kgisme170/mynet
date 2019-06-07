using System;

namespace DataApp1
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
    class UseObserver
    {
        public static void Test()
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
