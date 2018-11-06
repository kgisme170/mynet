import akka.actor._

object children extends App {

  case class CreateChild(name: String)

  case class Name(name: String)

  class Child extends Actor {
    var name = "No name"

    override def postStop: Unit = {
      println(s"D'oh! They killed me ($name): ${self.path}")
    }

    def receive = {
      case Name(name) => this.name = name
      case _ => println(s"Child $name got message.")
    }
  }

  class Parent extends Actor {
    def receive = {
      case CreateChild(name) =>
        // Parent creates a new Child here
        println(s"Parent about to create Child ($name) ...")
        val child = context.actorOf(Props[Child], name = s"$name")
        child ! Name(name)
      case _ => println(s"Parent got some other message.")
    }
  }

  val actorSystem = ActorSystem("ParentChildTest")
  val parent = actorSystem.actorOf(Props[Parent], name = "Parent")

  // send messages to Parent to create to child actors
  parent ! CreateChild("XiaoMing")
  parent ! CreateChild("XiaoLiang")
  Thread.sleep(500)

  // lookup XiaoMing, the kill it
  println("Sending XiaoMing a PoisonPill ... ")
  val xm = actorSystem.actorSelection("/user/Parent/XiaoMing")
  xm ! PoisonPill
  println("XiaoMing was killed")

  Thread.sleep(5000)
}