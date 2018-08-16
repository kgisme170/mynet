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
  val wordsReduceLocal = wordsRDD.reduceByKeyLocally(_+_).foreach(println)
  val wordsGroup = wordsRDD.groupByKey().map(w=>(w._1,w._2.sum)).foreach(println)
  wordsRDD.groupByKey().foreach(println)
  var rdd1 = sc.makeRDD(Array(("A",0),("A",2),("B",1),("B",2),("C",1)))
  println(rdd1.partitions.size)
}
