//actors is a deprecated package in favor of Akka
import scala.actors.Actor
import scala.actors.Actor._
class HiActor extends Actor{
    def act(){
        while(true){
            receive{
                case "Hi"=>println("hello")
            }
        }
    }
}
val actor1=new HiActor
actor1.start()
actor1 ! "Hi"

val actor2=actor{
    while(true){
        receive{
            case "Hi"=>println("hello")
        }
    }
}
actor2 ! "Hi"
