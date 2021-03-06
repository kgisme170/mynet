object ch06 extends App {

  object color extends Enumeration {
    type color = Value
    val Red, Yellow = Value
  }

  def doWhat(c: color.color) = {
    if (c == color.Red) "Stop"
    else "Start"
  }

  if (args.length > 0) {
    println("hello" + args(0))
    println(color.Red)
    println(doWhat(color.Yellow))
  }
}