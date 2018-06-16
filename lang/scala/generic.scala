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

class Pair[T<%Comparable[T]](val first:T,val second:T){
    def smaller =
        if (first.compareTo(second) < 0) first else second
}

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
object test0 extends App{
    val strv="123"
    val intv=123
    val strTest=new Test[String](strv)
    val intTest=new Test[Int](intv)
    val strTest2=new Test[Int](strv) //type mismatch
    val intTest2=new Test[String](intv) //type mismatch
    strTest.check
    intTest.check
    strTest2.check
    intTest2.check
    var a=2
    var b=3
    val pair=new Pair(a,b)
    5 times println("HI")
}
