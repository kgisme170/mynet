import scala.math._
val fun=ceil _
println(Array(3.14, 1.42, 2.0).map(fun))

val trile=(x:Double)=>3*x
for(a <- Array(3.14, 1.42, 2.0).map((x:Double)=>3*x))
    println(a)

def valueAtOneQuarter(f:(Double)=>Double)=f(0.25)
println(valueAtOneQuarter(ceil _))
println(valueAtOneQuarter(sqrt _))
println(valueAtOneQuarter(3 * _))
def mulBy(factor:Double)=(x:Double)=>factor*x
val quintuple=mulBy(5)
quintuple(20)
Array(3.14, 1.42, 2.0).map((x:Double)=>3*x).foreach(println _)
(1 to 9).filter(_ %2 == 0).foreach(println _)
println((1 to 9).reduceLeft(_+_))

def mulOneAtATime(x:Int)(y:Int)(z:Int)=x*y*z
println(mulOneAtATime(5)(6)(7))

val a=Array("hello")
val b=Array("Hello")
println(a.corresponds(b)(_.equalsIgnoreCase(_)))

def runInThread(block: =>Unit){
    new Thread{
        override def run(){block}
    }.start()
}
runInThread(
    {
        println("Hi");
        Thread.sleep(1000);
        println("Bye")
    }
)