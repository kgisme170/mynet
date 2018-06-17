class Test[T](val v:T){
    def check={
        if(v.isInstanceOf[String]){
            println("The param is String")
        }
        if(v.isInstanceOf[Int]){
            println("The param is Int")
        }
    }
}
def getMiddle[T](a:Array[T])=a(a.length/2)
/*
object Helpers{
    implicit class IntWithTime(x:Int){
        def times[A](f:=>A):Unit={
            def loop(current:Int):Unit=
                if (current>0){
                    f
                    loop(current-1)
                }
            loop(x)
        }
    }
}
*/
val gf=getMiddle[String] _
println(getMiddle(Array("Mary", "had", "a", "little", "lamb")))

val strv="123"
val intv=123
val strTest=new Test[String](strv)
val intTest=new Test[Int](intv)
strTest.check
intTest.check
var a="Red"
var b="Green"

class Pair[T<:Comparable[T]](val first:T,val second:T){
    def smaller =
        if (first.compareTo(second) < 0) first else second
}
class Pair1[T](val first:T,val second:T){
    def replaceFirst[R>:T](newFirst:R)=new Pair1[R](newFirst, second)
}
class Pair2[T<%Comparable[T]](val first:T,val second:T){
    def smaller =
        if (first.compareTo(second) < 0) first else second
}
class Pair3[T:Ordering](val first:T,val second:T){
    def smaller(implicit ord:Ordering[T]) =
        if(ord.compare(first, second) < 0) first else second
}
val p=new Pair(a,b)
println(p.smaller)
val p1=new Pair1(a,b)
p1.replaceFirst("ok")
val p2=new Pair2(2,3)
println(p2.smaller)
//5 times println("HI")
def makePair[T : Manifest](first: T,second: T){
    val r=new Array[T](2)
    r(0)=first
    r(1)=second
    r
}
val a1=makePair(1,2)
println(a1(1))