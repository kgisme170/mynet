import scala.io.Source
def readFile()
{
    val source=Source.fromFile("ch08.scala","UTF-8")
    val lineIterator=source.getLines
    for(a<-lineIterator.toArray)
        println(a)
    println(source.mkString)
    var iter=source.buffered
    while(iter.hasNext){
        println(iter.next)
    }
    val tokens=source.mkString.split("\\s+")
    for(t<-tokens)
        println(t)
}
def mapArray()
{
    val a=List(1,2,3,4)
    val numbers=a.map(_.toDouble+1.5)
    for(n<-numbers)
        println(n)
    import java.io.PrintWriter
    val out=new PrintWriter("numbers.txt")
    for(a<-numbers)out.println(a)
        out.close()
}

def getWebPage()
{
    val source1=Source.fromURL("http://www.baidu.com", "UTF-8")
    for(s<-source1.getLines)
        println(s)
}

def readDir()
{
    import java.io.File
    def subdirs(dir: File): Iterator[File] = {  
        val children = dir.listFiles.filter(_.isFile)  
        children.toIterator// ++ children.toIterator.flatMap(subdirs _)  
    }  
    for(d<-subdirs(new File(".")))
        println(d)
}
@SerialVersionUID(43L) class p(val a:Int=14,val b:Double=3.0) extends Serializable{
}
import java.io._
def errorFunction()
{
    val out = new ObjectOutputStream(new FileOutputStream("my.obj"))
    out.writeObject(new p(5,7))
    out.close()

    val in = new ObjectInputStream(new FileInputStream("my.obj"))
    val savedPerson=in.readObject.asInstanceOf[p]
    println(savedPerson.a)
}
import scala.collection.mutable.ArrayBuffer
class Person(var name:String) extends Serializable{
  val friends = new ArrayBuffer[Person]()
  def addFriend(friend : Person){
    friends += friend
  }
  override def toString() = {
    var str = "My name is " + name + " and my friends name is "
    friends.foreach(str += _.name + ",")
    str
  }
}
def errorFunction2()
{
    val p1 = new Person("JackChen")
    val p2 = new Person("Jhon·D")
    val p3 = new Person("Sunday")
    p1.addFriend(p2)
    p1.addFriend(p3)
    println(p1)
    val out = new ObjectOutputStream(new FileOutputStream("Person.obj"))
    out.writeObject(p1)
    out.close()
    val in = new ObjectInputStream(new FileInputStream("Person.obj"))
    val p_ = in.readObject().asInstanceOf[Person]
    println(p_)
}
def runProcess()
{
    import sys.process._
    "ifconfig" #| "grep IPv4" #> new File("output.txt") !

    val pr = Process("sh", new File("."), ("LANG", "en_US"))
    "ifconfig" #| pr
}
def runRegex()
{
    import scala.util.matching.Regex
    val numPattern="[0-9]+".r
    val s="99 bottles,98 bottles"
    for(matchString<-numPattern.findAllIn(s))
        println(matchString)

    val m1=numPattern.findFirstIn(s)
    println(m1)

    val numitem="([0-9]+) ([a-z]+)".r
    val numitem(num, item)="99 bottles"
    println(num)
    println(item)
}
errorFunction