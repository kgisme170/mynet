using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Text;

namespace NUnitTestProject_core
{
    class ObserverInterface
    {
        class Position
        {
            public int X { get; set; }
            public int Y { get; set; }
            class PositionObserver : IObserver<Position>
            {
                IDisposable _unsubscribe;
                readonly string _obName;
                public PositionObserver(string obName)
                {
                    _obName = obName;
                }

                public void Subscribe(IObservable<Position> position)
                {
                    if (position != null)
                    {
                        _unsubscribe = position.Subscribe(this);
                    }
                }

                public void Unsubscribe()
                {
                    _unsubscribe.Dispose();
                }

                // implement interface
                public void OnCompleted()
                {
                    Console.WriteLine("obName:" + _obName + " OnComplete!!");
                }

                public void OnError(Exception error)
                {
                    throw new NotImplementedException();
                }

                public void OnNext(Position value)
                {
                    Console.WriteLine("obName:" + _obName + ",X=" + value.X + ",Y=" + value.Y);
                }
            }

            class PositionObservable : IObservable<Position>
            {
                readonly List<IObserver<Position>> listOb = new List<IObserver<Position>>();

                public void Fire(Position p)
                {
                    foreach (var ib in listOb)
                    {
                        ib.OnNext(p);
                    }
                }

                class Unsubscribe : IDisposable
                {
                    readonly List<IObserver<Position>> _listOb;
                    readonly IObserver<Position> _observer;
                    public Unsubscribe(List<IObserver<Position>> listOb, IObserver<Position> observer)
                    {
                        _listOb = listOb;
                        _observer = observer;
                    }
                    public void Dispose()
                    {
                        if (_observer != null && _listOb.Contains(_observer))
                        {
                            _listOb.Remove(_observer);
                        }
                    }
                }

                // implement interface
                public IDisposable Subscribe(IObserver<Position> observer)
                {
                    if (!listOb.Contains(observer))
                    {
                        listOb.Add(observer);
                    }
                    return new Unsubscribe(listOb, observer);
                }
            }

            [Test]
            public static void ObserverTest()
            {
                var lo = new Position() { X = 2, Y = 3 };
                var ob = new PositionObservable();
                var observer1 = new PositionObserver("observer1");
                var observer2 = new PositionObserver("observer2");

                observer1.Subscribe(ob);
                observer2.Subscribe(ob);
                ob.Fire(lo);
                observer1.Unsubscribe();
                ob.Fire(lo);
            }
        }
    }
}
