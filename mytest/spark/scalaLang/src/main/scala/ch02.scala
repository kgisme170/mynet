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
}
