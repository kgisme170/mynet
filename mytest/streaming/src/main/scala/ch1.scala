import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf

object ch1
{
  def main(args:Array[String])
  {
    val sc = new StreamingContext(new SparkConf().setMaster("local").setAppName("socketstream"),Seconds(10))
    val mystreamRDD = sc.socketTextStream("www.baidu.com",80)
    mystreamRDD.print()
    sc.start()
    sc.awaitTermination()
  }
}