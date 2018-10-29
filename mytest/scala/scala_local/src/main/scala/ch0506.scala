object ch0506 extends App {

  val fred = new Person()
  val obj = new p4("my", 2)
  myCounter.increment
  println(myCounter.current)
  val chatter = new Network
  val myFace = new Network
  fred.age = 30
  val fred2 = chatter.join("Fred")
  val wilma = chatter.join("wilma")
  val a = Account(3)
  println(obj.description)

  import scala.collection.mutable._
  val b = Account(4)
  val c = Account(30)
  var myCounter = new Counter

  class Counter {
    private var value = 0

    def increment() {
      value += 1
    }

    def current = value
  }

  class Person {
    private var privateAge = 0

    def age = privateAge

    def age_=(newValue: Int) {
      if (newValue > privateAge) privateAge = newValue
    }
  }
  fred2.contacts += wilma

  class p3 {
    private var name = ""
    private var age = 0

    def this(name: String) {
      this()
      this.name = name
    }

    def this(name: String, age: Int) {
      this(name)
      this.age = age
    }
  }

  class p4(val name: String, private var age: Int) {
    def increment() {
      age += 1
    }

    def description = name + age
  }

  class Network {

    private val members = new ArrayBuffer[Member]

    def join(name: String) = {
      println(Network.this.members.length)
      val m = new Member(name)
      members += m
      m
    }

    class Member(val name: String) {
      val contacts = new ArrayBuffer[Member]
    }
  }
  println(a.deposit(20))
  println(a.id)

  class Account(val Id: Int, initialBalance: Double = 1) {
    val id = Account.newUniqueNumber()
    private var balance = 0.0

    def deposit(amount: Double) {
      balance += amount
    }
  }
  println(b.id)

  object Account {
    private var lastNumber = 0

    def apply(initialBalance: Double) = {
      new Account(newUniqueNumber(), initialBalance)
    }

    private def newUniqueNumber() = {
      lastNumber += 1;
      lastNumber
    }
  }
  println(c.id)
}