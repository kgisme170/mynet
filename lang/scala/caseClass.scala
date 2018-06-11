sealed abstract class Person
case class Student(name:String, age:Int) extends Person
case class Teacher(name:String) extends Person

case class SchoolClass(classDescription:String,persons:Person*)
object patternMatch extends App{
    for(i<- 1 to 100){
        i match{
            case 10=>println("case 10")
            case 20=>println("case 20")
            case _ if(i%5==0)=>println(i)
            case _ if(i%20==0)=>println(i)
            case _ =>
        }
    }
    val p:Person=Student("me",20)
    p match{
        case Student(name,age)=>println(name+":"+age)
        case Teacher(name)=>println("Teacher")
    }
    val sc=SchoolClass("thisclass", Teacher("you"), Student("Him", 20))
    sc match{
        case SchoolClass(_,_,Student(name,age))=>println(name)
        case _ => println("Nobody")
    }
}