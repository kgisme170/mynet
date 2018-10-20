import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object streamsocket {
  def main(args: Array[String]) {
    //Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    //Logger.getLogger("org.eclipse.jetty.Server").setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("TCPOnStreaming example").setMaster("local[4]")
    //val sc = new SparkContext(conf)//不能再创建SparkContext
    val ssc = new StreamingContext(conf, Seconds(2))
    //get the socket Streaming data
    val socketStreaming = ssc.socketTextStream("localhost", 9087)
    //hostname不能是master
    val data = socketStreaming.flatMap(x => x.split(" ")).map(x => (x, 1))
    data.reduceByKey(_ + _).print()

    ssc.start()
    ssc.awaitTermination()
  }
}