object ch03 extends App {
  lazy val words = scala.io.Source.fromFile("/tmp/entsafeinstall.log").mkString
  var s = 8 * 5 + 2
  println(s)
  println('a', "a".toUpperCase)
  println(1.toString())
  println(1.to(10))
  println(3.+(5))
  val x: BigDecimal = 1234235390 //为什么不能用更大的数值
  println()
  var c = 3
  c += 1
  println(c)
  var r = 3

  val v = for (i <- 1 to 10) yield i * 2
  println(x * x * x)

  import scala.math._

  printf("%f,%f,%f\n", sqrt(2), pow(2, 4), min(3, Pi))
  println(BigInt.probablePrime(20, scala.util.Random))
  for (i <- 1 to 10) print(i)
  println()
  println("Hello".apply(4))
  println("Hello" (3))
  for (e <- Array(1, 4, 9, 16)) print(e)
  println()
  println("Harry".patch(1, "ung", 2))
  println(if (x > 0) 1 else -1)
  println(if (x > 0) "positive" else -1)
  println(if (x < 0) 1)
  println(x)
  val url = "http://horstmann.com/fred-tiny.gif"
  if (r > 0) {
    r = r * r;
    r -= 1
  }

  println(s)
  for (ch <- "hello") print(ch)

  import scala.util.control.Breaks._

  breakable {
    for (i <- 1.to(10)) {
      if (i % 5 == 0) break
      else print(i)
    }
  }
  println()
  for (i <- 1 to 3; j <- 1 to 3 if i != j) printf("%d,", i + j)
  println()

  for (i <- 1 to 3; from = 4 - i; j <- from to 3) printf("%d,", i * j)
  println(ab(-34))
  var dis = {
    val x = 3;
    val y = 4;
    sqrt(x * x + y * y)
  }

  println(ad(4))

  def ab(x: Double) = if (x > 0) x else -x

  println(sum(1, 2, 4, 5))
  println(sum(1 to 5: _*))

  def ad(x: Double, y: Double = 3) = x + y

  box("hello")

  def sum(args: Int*) = {
    var s = 0
    for (a <- args) s += a
    s
  }
  println(words)

  if (x > 0) sqrt(2324) else throw new IllegalArgumentException("x not found")

  import java.io._
  import java.net._

  def box(s: String) {
    var border = "-" * s.length + "--\n"
    println(border + "|" + s + "|\n" + border)
  }
  try {
    new URL(url)
  } catch {
    case _: MalformedURLException => println("Bad URL:" + url)
    case ex: IOException => ex.printStackTrace()
  }
  try {
    print(3 / 0)
  } catch {
    case _: Exception => println("caught")
  } finally {
    println("ok")
  }
}