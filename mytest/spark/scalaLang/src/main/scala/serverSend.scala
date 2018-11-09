import java.net._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object serverSend {

  def handleClient(s: Socket): Unit = {
    val in = s.getInputStream
    val out = s.getOutputStream
    while (s.isConnected) {
      Thread.sleep(1000) // wait for 1000 millisecond
      out.write("Pong".getBytes)
    }
  }

  def main(args: Array[String]) {
    val server = new ServerSocket(9999)
    while (true) {
      val s: Socket = server.accept()
      Future {
        handleClient(s)
      }
    }
  }
}