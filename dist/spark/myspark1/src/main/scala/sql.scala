import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
object sql extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
}