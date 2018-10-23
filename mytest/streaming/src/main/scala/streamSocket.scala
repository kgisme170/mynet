import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object streamSocket {
  def main(args: Array[String]) {
    //Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    //Logger.getLogger("org.eclipse.jetty.Server").setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("TCPOnStreaming example").setMaster("local[4]")
    //conf.setJars(Array("."))//会在当前目录寻找一个叫做/jars的目录，然后继续找执行的jar包
    val ssc = new StreamingContext(conf, Seconds(2))
    val socketStreaming = ssc.socketTextStream("localhost", 9087)
    val data = socketStreaming.flatMap(x => x.split(" ")).map(x => (x, 1))
    data.reduceByKey(_ + _).print()
    ssc.start()
    ssc.awaitTermination()
  }
}