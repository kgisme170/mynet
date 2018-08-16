import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
object he extends App{
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val input = sc.parallelize(List(1,2,3,4))
  val result = input.map(x=>x*x)
  println(result.collect().mkString(","))
  println("test")

  val words = Array("one", "two", "three", "four", "two", "two", "one")
  val wordsRDD = sc.parallelize(words).map(word=>(word, 1))
  val wordsReduce = wordsRDD.reduceByKey(_+_).collect().foreach(println)
  val wordsGroup = wordsRDD.groupByKey().map(w=>(w._1,w._2.sum)).collect().foreach(println)
}
