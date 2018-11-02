import akka.actor._

object pingPong extends App {

  class Ping(pong: ActorRef) extends Actor {
    var count = 0

    def receive = {
      case StartMessage =>
        incrementAndPrint
        pong ! PongMessage
      case PingMessage =>
        incrementAndPrint
        if (count > 99) {
          sender ! StopMessage
          println("ping stopped")
          context.stop(self)
        }
        else
          sender ! PongMessage
      case _ => println("Ping got unexpected information")
    }

    def incrementAndPrint {
      count += 1;
      println(s"$count:ping")
    }
  }

  class Pong extends Actor {
    var count = 0

    def receive = {
      case StopMessage =>
        println("pong stopped")
        context.stop(self)
      case PongMessage =>
        count += 1
        println(s"$count:pong")
        sender ! PingMessage
      case _ => println("Pong got unexpected information")
    }
  }

  case object PingMessage

  case object PongMessage

  case object StartMessage

  case object StopMessage

  val system = ActorSystem("PingPongTest")
  val pongActor = system.actorOf(Props[Pong], name = "pong")
  val pingActor = system.actorOf(Props(new Ping(pongActor)),
    name = "ping")
  pingActor ! StartMessage
}