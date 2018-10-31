import java.io.FileNotFoundException

object ch01 extends App {
  for (i <- 1 to 10) println(i)

  import scala.util.Random
  import scala.util.control.Breaks._

  breakable {
    while (true) {
      val r = new Random()
      val i = r.nextInt(10)
      println(i)
      if (i > 5) {
        break
      }
    }
  }

  try {
    import scala.io.Source
    val file = Source.fromFile("/tmp/file01")
    val lines = file.getLines()
    for (l <- lines) {
      println(l)
    }
  } catch {
    case ex: FileNotFoundException => println("找不到文件")
    case ex: Exception => println(ex)
  } finally {
    println("結束");
  }
}
