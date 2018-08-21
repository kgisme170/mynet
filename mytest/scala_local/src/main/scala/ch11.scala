object ch11 extends App {
  1 to 10
  1.to(10)
  1 -> 10
  1.->(10)
  println(-1 toString)
  val a1 = 1
  print(-a1 toString)
  println(1 :: 2 :: Nil)

  class Complex(val real: Int, val imaginary: Int) {
    def +(operand: Complex): Complex = {
      new Complex(real + operand.real, imaginary + operand.imaginary)
    }

    override def toString(): String = {
      real + (if (imaginary < 0) "" else "+") + imaginary + "i"
    }
  }

  object Complex {
    def unapply(input: Complex) = Some((input.real, input.imaginary))
  }

  val c1 = new Complex(1, 2)
  val c2 = new Complex(2, -3)
  val sum = c1 + c2
  println("(" + c1 + ")+ (" + c2 + ")=" + sum)
  var Complex(a, b) = c1 + c2
  println(a)
  println(b)

  class Delta(val x: Int, val y: Int) {
    def *(operand: Delta): Int = {
      x * operand.x + y * operand.y
    }
  }

  object Delta {
    def apply(x: Int, y: Int) = new Delta(x, y)
  }

  val result = Delta(3, 3) * Delta(4, 4)
  println(result)

  object Number {
    def unapply(input: String): Option[Int] =
      try {
        Some(Integer.parseInt(input.trim))
      } catch {
        case ex: NumberFormatException => None
      }
  }

  object IsCompound {
    def unapply(input: String) = input.contains(" ")
  }

  val Number(n) = "1729"
  println(n)

  object Name {
    def unapplySeq(input: String): Option[Seq[String]] =
      if (input.trim == "") None else Some(input.trim.split("\\s+"))
  }

  "hello world" match {
    case Name(first, last) => println("2")
    case Name(first, middle, last) => println("3")
  }
}