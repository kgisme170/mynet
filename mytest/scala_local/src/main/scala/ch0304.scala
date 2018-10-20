object ch0304 extends App {
  val nums = new Array[Int](10)
  val n2 = Array(1, 3, 5, 7)
  val n3 = Array(1.0, 3.0, 5, 7)
  val sa = ("Hello", "World")

  import scala.collection.mutable.ArrayBuffer

  val b1 = ArrayBuffer[Int](1, 3, 5)
  b1 += 7
  b1 += (2, 4, 6)
  b1 ++= Array(8)
  b1.trimEnd(3)
  println(b1)
  b1.insert(2, 6)
  b1.insert(2, 7, 8, 9)
  println(b1)
  b1.remove(2, 3)
  println(b1)
  val a = b1.toArray
  for (i <- 0 until b1.length) print(b1(i))
  println()
  println((0 until a.length).reverse)
  val v2 = for (e <- b1 if e % 2 == 0) yield 2 * e
  println(v2)
  println(b1.filter(_ % 2 == 0).map(2 * _))
  println(b1.sum)
  println(a.max)
  println(b1.sortWith(_ > _))
  scala.util.Sorting.quickSort(a)
  println(a)
  val matrix = Array.ofDim[Double](3, 4)
  matrix(2)(3) = 42
  println(matrix)
  val command = ArrayBuffer("ls", "-al", "/home/my")
  for (i <- 0 until triangle.length)
    triangle(i) = new Array[Int](i + 1)

  import scala.collection.JavaConversions.bufferAsJavaList
  import scala.collection.mutable.ArrayBuffer
  val pb = new ProcessBuilder(command)
  val cmd: Buffer[String] = pb.command()

  import scala.collection.JavaConversions.asScalaBuffer
  import scala.collection._
  import scala.collection.mutable.Buffer
  val s3 = mutable.HashMap[String, Int]()
  val s4 = s3 + ("my" -> 8)
  val props: scala.collection.Map[String, String] = System.getProperties()
  val attrs = Map(FAMILY -> "Serif", SIZE -> 12)
  println(s2.getOrElse("bob", 0))
  s3 += ("Bob" -> 10, "Fred" -> 7)
  val t = (2, 3, 4)
  println(scores.keySet)
  for (v <- scores.values) println(v)
  for ((k, v) <- scores) printf("%s,%d\n", k, v)

  import scala.collection.JavaConversions.mapAsScalaMap
  val (first, second, _) = t

  import scala.collection.JavaConversions.propertiesAsScalaMap
  val symbols = Array(1, 2, 3)

  import java.awt.font.TextAttribute._
  val counts = Array(3, 4, 5)
  //val font=new java.awt.Font(attrs)
  println((2, 4, 6)._2)
  var triangle = new Array[Array[Int]](10)
  var scores = immutable.SortedMap("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
  println(first)
  var s2 = Map("Alice" -> 10)
  var s5: mutable.Map[String, Int] = new java.util.TreeMap[String, Int]
  println(symbols.zip(counts))
}