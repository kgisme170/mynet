import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel
object  action extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val data = sc.parallelize(List(1,2,1,3,1,4))
  val sum = data.reduce(_+_)
  println(sum)
  data.persist(StorageLevel.DISK_ONLY)

  val data2 = List((1,3),(1,2),(1,4),(2,3),(3,6),(3,8))
  val result2 = sc.parallelize(data2)
  result2.collect()
  result2.keys.foreach(println)
  result2.values.foreach(println)
  result2.flatMapValues(x=>(x to 5)).foreach(println)
  result2.sortByKey().foreach(println)
  result2.filter{case (k,v)=>v<=3}.foreach(println)
  println("-------------")
  println(result2.reduceByKey(_+_))
  println("-------------")
  result2.reduceByKey(_+_, 10).foreach(println)
  println("-------------")
  println(result2.countByKey())
  println("-------------")

  val d1 = sc.parallelize(List((1,"hadoop"),(2,"spark")))
  val d2 = sc.parallelize(List((1,"java"),(2,"scala"),(3,"python")))
  val d3 = sc.parallelize(List((1,"hdfs"),(2,"hbase"),(3,"hive")))
  val res = d1.cogroup(d2,d3)
  res.foreach(println)
}