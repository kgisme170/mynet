import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object ch01 extends App{
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val lines = sc.textFile("./ch01.scala")
  println(lines.count())
  println(lines.first())
  val sparklines = lines.filter(line=>line.contains("spark"))
  println(sparklines)
  val slines = sparklines.collect
  println(slines.mkString(","))
  val words = lines.flatMap(line=>line.split( " "))
  val counts = words.map(word=>(word,1)).reduceByKey{case (x,y)=>x+y}
  counts.saveAsTextFile("../../out/results/")
}
