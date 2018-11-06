import scala.collection.mutable.ArrayBuffer

object ch02 extends App {
  val arrStr = Array("a1", "a2")
  val arrInt = Array[Int](10)
  println(arrInt.size)
  val arrBuf = ArrayBuffer[Int](10, 20, 30)
  arrBuf += 1
  for (i <- arrBuf) print(i + " ")
  println
  arrBuf.trimEnd(2)
  for (i <- arrBuf) print(i + " ")
  println
  val O = Array(3, "str")
  for (o <- O) print(o)
  println("\n===")
  for (_ <- O) print(_)
  println("\n===")

  val bigData = Map("a1" -> 1, "a2" -> 2, "a3" -> 3)
  println(bigData.drop(1))
  val bigData2 = scala.collection.mutable.Map("a1" -> 1, "a2" -> 2, "a3" -> 3)
  bigData2 += ("a4" -> 4)
  bigData2 += ("a4" -> 4)
  for(d<-bigData2)println(d)
  println
  val t1 = (1,2,"hello")
  println(t1._1)
}
