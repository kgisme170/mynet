import akka.actor._
import akka.pattern.gracefulStop
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.language.postfixOps

case object TestActorStop

class TestActor extends Actor {
  def receive = {
    case TestActorStop =>
      context.stop(self)
    case _ => println("TestActor got message")
  }
  override def postStop {println("TestActor: postStop")}
}

object stop extends App{
  val system = ActorSystem("GracefulStopTest")
  val testActor = system.actorOf(Props[TestActor], name="TestActor")
  // try to stop the actor graceful
  try {
    val stopped: Future[Boolean] = gracefulStop(testActor, 2 seconds, TestActorStop)
    Await.result(stopped, 3 seconds)
    println("testActor was stopped")
  } catch {
    case e: akka.pattern.AskTimeoutException => e.printStackTrace
  } finally {
    //system.shutdown
  }
}