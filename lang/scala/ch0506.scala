class Counter{
    private var value=0
    def increment(){value+=1}
    def current=value
}
var myCounter=new Counter
myCounter.increment
println(myCounter.current)
class Person{
    private var privateAge=0
    def age=privateAge
    def age_=(newValue:Int){
        if(newValue>privateAge)privateAge=newValue
    }
}
val fred=new Person()
fred.age=30
/*
import scala.reflect.BeanProperty
class p2{
    @BeanProperty var name:String=_
}
val p=new p2()
println(p.getName())*/

class p3{
    private var name=""
    private var age=0
    def this(name:String){
        this()
        this.name=name
    }
    def this(name:String,age:Int){
        this(name)
        this.age=age
    }
}
class p4(val name:String,private var age:Int){
    def increment(){
        age+=1
    }
    def description=name+age
}
val obj=new p4("my",2)
println(obj.description)
import scala.collection.mutable._
class Network{
    class Member(val name:String){
        val contacts=new ArrayBuffer[Member]
    }
    private val members=new ArrayBuffer[Member]
    def join(name:String)={
        println(Network.this.members.length)
        val m=new Member(name)
        members+=m
        m
    }
}
val chatter=new Network
val myFace=new Network
val fred2=chatter.join("Fred")
val wilma=chatter.join("wilma")
fred2.contacts+=wilma

class Account(val Id:Int,initialBalance:Double=1){
    val id=Account.newUniqueNumber()
    private var balance=0.0
    def deposit(amount:Double){balance+=amount}
}
object Account{
    private var lastNumber=0
    private def newUniqueNumber()={lastNumber+=1;lastNumber}
    def apply(initialBalance:Double) ={
        new Account(newUniqueNumber(),initialBalance)
    }
}
val a = Account(3)
println(a.deposit(20))
println(a.id)
val b = Account(4)
println(b.id)
val c=Account(30)
println(c.id)
