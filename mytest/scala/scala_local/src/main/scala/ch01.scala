object ch01 extends App{
  for(i<-1 to 10)println(i)
  import scala.util.control.Breaks._
  import scala.util.Random
  breakable{
    while(true){
      val r= new Random()
      val i = r.nextInt(10)
      println(i)
      if(i>5){
        break
      }
    }
  }
}
