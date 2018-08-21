import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class HelloActor extends Actor{
  override def receive = {
    case "hello" => println("hello received")
    case _ => println("other messages")
  }
}

object actor01 extends App{
  val system = ActorSystem("actor01")
  val helloActor = system.actorOf(Props[HelloActor],name="myActor")
  helloActor!"msg"
  helloActor!"hello"
}
