import akka.actor._

object kill extends App {

  val system = ActorSystem("KillTestSystem")
  val number5 = system.actorOf(Props[Number5], name = "Number5")

  class Number5 extends Actor {
    def receive = {
      case _ => println("Number 5 got a message")
    }

    override def preStart {
      println("Number 5 is alive")
    }

    override def postStop {
      println("Number 5::postStop called")
    }

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      println("Number 5::preRestart called")
    }

    override def postRestart(reason: Throwable): Unit = {
      println("Number 5::postRestart called")
    }
  }
  number5 ! "hello"
  number5 ! Kill
}