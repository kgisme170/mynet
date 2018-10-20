object ch14 extends App {

  sealed abstract class Person

  case class Student(name: String, age: Int) extends Person

  case class Teacher(name: String) extends Person

  case class SchoolClass(classDescription: String, persons: Person*)

  object patternMatch extends App {
    for (i <- 1 to 100) {
      i match {
        case 10 => println("case 10")
        case 20 => println("case 20")
        case _ if (i % 5 == 0) => println(i)
        case _ if (i % 20 == 0) => println(i)
        case _ =>
      }
    }
    val p: Person = Student("me", 20)
    p match {
      case Student(name, age) => println(name + ":" + age)
      case Teacher(name) => println("Teacher")
    }
    val sc = SchoolClass("thisclass", Teacher("you"), Student("Him", 20))
    sc match {
      case SchoolClass(_, _, Student(name, age)) => println(name)
      case _ => println("Nobody")
    }

    Array(3, 4, 5) match {
      case Array(0) => println("0")
      case Array(x, y) => println(x + y)
      case Array(3, _*) => println("OK")
      case _ => println("else")
    }
    List(1, 7) match {
      case 0 :: Nil => println("0")
      case x :: y :: Nil => println(x + y)
      case 0 :: tail => println("0 begin")
      case _ => println("else")
    }
    val (q, r) = BigInt(20) /% 3
    println(q)
    println(r)

    import scala.collection.mutable._

    val m = Map("key1" -> 23, "key2" -> 20, "key3" -> 0)
    val fm = m.retain((k, v) => v > 0)
    println(fm)
    // 产生嵌套对象
    val b = Bundle("Father's day special", 20.0, Article("Scala for the Impatient", 39.95),
      Bundle("Anchor Distillery Sampler", 10.0, Article("Old Potrero Straight Rye Whisky", 79.95),
        Article("Junipero Gin", 32.95)))

    abstract class Item

    case class Article(description: String, price: Double) extends Item

    case class Bundle(description: String, discount: Double, items: Item*) extends Item

    // 模式匹配到特定的嵌套，比如
    b match {
      case Bundle(_, _, Article(descr, _), _*) => println(descr)
    }
    println("-3+4".collect({ case '+' => 1; case '-' => -1 }))
  }

}