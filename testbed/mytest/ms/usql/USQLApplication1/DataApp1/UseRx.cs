using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reactive.Linq;
using System.IO;

namespace DataApp1
{
    class UseRx
    {
        public static void Test()
        {
            //使用Range方法返回Observable集合
            IObservable<Int32> input = Observable.Range(1, 15);
            input.Where(i => i % 2 == 0).Subscribe(x => Console.Write("{0} ", x));
            Console.WriteLine();

            //使用Array返回Observabale集合
            var myArray = new[] { 1, 3, 5, 7, 9 };
            IObservable<Int32> varmyObservable = myArray.ToObservable();
            varmyObservable.Subscribe(x => Console.WriteLine("Integer:{0}", x));
            Console.WriteLine();
            //Take操作符，用来指定获取集合中的前几项
            var take = new[] { 1, 2, 3, 4, 5, 4, 3, 2, 1 }.ToObservable();
            take.Take(5).Select(x => x * 10).Subscribe(x => Console.WriteLine(x));
            Console.WriteLine();
            //Skip操作符表示跳过集合中的n条记录。
            var skip = new[] { 1, 2, 3, 4, 5, 4, 3, 2, 1 }.ToObservable();
            skip.Skip(6).Select(x => x * 10).Subscribe(x => Console.WriteLine(x));
            Console.WriteLine();
            //Distinct操作符用来去除集合中的非重复数据。
            var distinct = new[] { 1, 2, 3, 4, 5, 4, 3, 2, 1 }.ToObservable();
            distinct.Distinct().Select(x => x * 10).Subscribe(x => Console.WriteLine(x));
            //Rx也需要释放资源
            Console.WriteLine();
            var ObservableStrings = Observable.Using<char, StreamReader>(
                () => new StreamReader(new FileStream("randomtext.txt", FileMode.Open)),
                streamReader => (streamReader.ReadToEnd().Select(str => str)).ToObservable()
                );
            ObservableStrings.Subscribe(Console.Write);
            Console.WriteLine();
            //在Rx中Zip是将两个Observable对象合并为一个新的Observable对象。
            var numberCitys = varmyObservable.Zip(input, (range, array) => range + ":" + array);
            numberCitys.Subscribe(Console.WriteLine);
            Console.ReadKey();
        }
    }
}
