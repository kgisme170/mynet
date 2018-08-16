import org.apache.spark.{SparkConf, SparkContext}
object action extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val data = sc.parallelize(List(1,2,1,3,1,4))
  val sum = data.reduce(_+_)
  println(sum)
}