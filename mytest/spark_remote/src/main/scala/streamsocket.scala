import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object streamsocket {
  def main(args: Array[String]) {

    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.Server").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("TCPOnStreaming example").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(5))
    //get the socket Streaming data
    val socketStreaming = ssc.socketTextStream("master",7000)

    val data = socketStreaming.map(x =>(x,1))
    data.print()

    ssc.start()
    ssc.awaitTermination()
  }
}