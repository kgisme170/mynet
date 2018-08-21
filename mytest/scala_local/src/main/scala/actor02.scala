import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class my(x:Int, y:Int) extends Actor{
  override def receive = {
    case "hello" => println("hello received")
    case _ => println("other messages")
  }
}

object actor02 extends App{
  val system = ActorSystem("actor01")
  val helloActor = system.actorOf(Props[my],"my")//参数?
  helloActor!"msg"
  helloActor!"hello"
}
