import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.Duration
import org.apache.spark.streaming.Seconds
object streamlog extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  conf.set("spark.sql.codegen", "true")
  val ssc = new StreamingContext(conf, Seconds(1))
  val lines = ssc.socketTextStream("localhost", 7000)
  //val infoLines = lines.filter(_.contains("info"))
  lines.print()
  lines.saveAsTextFiles("/home/a/ss")
  ssc.start()
  ssc.awaitTermination()
}