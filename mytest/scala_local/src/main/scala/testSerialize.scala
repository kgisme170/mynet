//命令行下面可以用scala -nc执行
object testSerialize extends App {
    @SerialVersionUID(43L) class p(val a:Int=14,val b:Double=3.0) extends Serializable{
    }
    import java.io._

    val out = new ObjectOutputStream(new FileOutputStream("my.obj"))
    out.writeObject(new p(5,7))
    out.close()

    val in = new ObjectInputStream(new FileInputStream("my.obj"))
    val savedPerson=in.readObject.asInstanceOf[p]
    println(savedPerson.a)
}