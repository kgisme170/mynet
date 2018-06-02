val nums=new Array[Int](10)
val n2=Array(1,3,5,7)
val n3=Array(1.0,3.0,5,7)
val sa=("Hello", "World")

import scala.collection.mutable.ArrayBuffer
val b1=ArrayBuffer[Int](1,3,5)
b1+=7
b1+=(2,4,6)
b1++=Array(8)
b1.trimEnd(3)
println(b1)
b1.insert(2,6)
b1.insert(2,7,8,9)
println(b1)
b1.remove(2,3)
println(b1)
val a=b1.toArray
for(i<-0 until b1.length)print(b1(i))
println()
println((0 until a.length).reverse)
val v2=for(e <-b1 if e%2==0)yield 2*e
println(v2)
println(b1.filter(_%2==0).map(2*_))
println(b1.sum)
println(a.max)
println(b1.sortWith(_>_))
scala.util.Sorting.quickSort(a)
println(a)
val matrix=Array.ofDim[Double](3,4)
matrix(2)(3)=42
println(matrix)
var triangle=new Array[Array[Int]](10)
for(i<- 0 until triangle.length)
    triangle(i)=new Array[Int](i+1)
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.mutable.ArrayBuffer
val command=ArrayBuffer("ls","-al","/home/my")
val pb=new ProcessBuilder(command)

import scala.collection._
import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.mutable.Buffer
val cmd:Buffer[String]=pb.command()
var scores=immutable.SortedMap("Alice"->10,"Bob"->3,"Cindy"->8)
var s2=Map("Alice"->10)
val s3=mutable.HashMap[String,Int]()
println(s2.getOrElse("bob",0))
s3+=("Bob"->10,"Fred"->7)
val s4=s3+("my"->8)
println(scores.keySet)
for (v<-scores.values)println(v)
for ((k,v)<-scores)printf("%s,%d\n",k,v)

import scala.collection.JavaConversions.mapAsScalaMap
var s5:mutable.Map[String,Int]=new java.util.TreeMap[String,Int]
import scala.collection.JavaConversions.propertiesAsScalaMap
val props:scala.collection.Map[String,String]=System.getProperties()

import scala.collection.JavaConversions.mapAsJavaMap
import java.awt.font.TextAttribute._
val attrs=Map(FAMILY->"Serif",SIZE->12)
//val font=new java.awt.Font(attrs)
println((2,4,6)._2)
val t=(2,3,4)
val (first,second,_)=t
println(first)
val symbols=Array(1,2,3)
val counts=Array(3,4,5)
println(symbols.zip(counts))