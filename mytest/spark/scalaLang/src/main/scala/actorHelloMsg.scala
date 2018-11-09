import akka.actor.{Actor, ActorSystem, Props}

object actorHelloMsg extends App {

  class HelloActor extends Actor {
    override def receive = {
      case "hello" => println("hello received")
      case _ => println("other messages")
    }
  }

  val system = ActorSystem("actor01")
  val helloActor = system.actorOf(Props[HelloActor], name = "myActor")
  helloActor ! "msg"
  helloActor ! "hello"
}