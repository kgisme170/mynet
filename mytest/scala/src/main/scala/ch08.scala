object ch08 extends App {

  val p = new c2("ab", 3)
  val alien = new Person() {
    def greeting = "Greetings"
  }

  def meet(p: Person {def greeting: String}) {
    println(p.greeting)
  }
  if (p.isInstanceOf[c2]) {
    println("is")
  }
  if (p.getClass == classOf[c2]) {
    println("is")
  }
  p match {
    case c: c1 => c.print()
    case _ => println("ok")
  }

  abstract class IPerson(val name: String) {
    val a: Int

    def id: Int
  }

  class c1(a: Int) {
    def print() {
      println(a)
    }
  }

  class c2(val name: String, val a: Int) extends c1(a) {
    override def toString = getClass.getName + "[name=" + name + "]"

    def print(b: Int) {
      println(b)
    }
  }

  meet(alien)

  class Person {}

  class Employee(name: String) extends IPerson(name) {
    val a = 3

    def id = name.hashCode
  }

  class Creature {
    val range: Int = 10
    val env: Array[Int] = new Array[Int](range)
  }

  class Ant extends {
    override val range = 2
  } with Creature {
    final override def equals(other: Any) = {
      val that = other.asInstanceOf[Ant]
      if (that == null) false
      else range == that.range
    }
  }

}