import scala.io.Source
val source=Source.fromFile("ch08.scala","UTF-8")
val lineIterator=source.getLines
//for(a<-lineIterator.toArray)
//    println(a)
//println(source.mkString)
//var iter=source.buffered
//while(iter.hasNext){
//    println(iter.next)
//}
val tokens=source.mkString.split("\\s+")
for(t<-tokens)
    println(t)
val a=List(1,2,3,4)
val numbers=a.map(_.toDouble+1.5)
for(n<-numbers)
    println(n)
val source1=Source.fromURL("http://www.baidu.com", "UTF-8")
for(s<-source1.getLines)
    println(s)
import java.io.PrintWriter
val out=new PrintWriter("numbers.txt")
for(a<-numbers)out.println(a)
out.close()

import java.io.File
def subdirs(dir: File): Iterator[File] = {  
    val children = dir.listFiles.filter(_.isFile)  
    children.toIterator// ++ children.toIterator.flatMap(subdirs _)  
}  
for(d<-subdirs(new File(".")))
    println(d)

@SerialVersionUID(43L) class Person extends Serializable{
    val a:Int = 1
}