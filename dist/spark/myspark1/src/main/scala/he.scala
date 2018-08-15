import java.io.File

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
object he extends App{
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val input = sc.parallelize(List(1,2,3,4))
  val result = input.map(x=>x*x)
  println(result.collect().mkString(","))
  println("test")
}
