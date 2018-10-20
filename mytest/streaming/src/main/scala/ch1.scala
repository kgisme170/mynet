import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.DStream

object ch1 {
  def main(args: Array[String]) {
    val sc = new StreamingContext(new SparkConf().setMaster("local").setAppName("socketstream"), Seconds(10))
    val ms = sc.socketTextStream("www.baidu.com", 80)
    ms.print()
    sc.start()
    sc.awaitTermination()
  }

  def printValues(stream: DStream[(String, Int)],
                  streamCtx: StreamingContext): Unit = {
    def foreachFunc = (rdd: RDD[(String, Int)]) => {
      val array = rdd.collect()
      println("------Start printing results-------")
      for (res <- array) {
        println(res)
      }
      println("------Start printing results-------")
    }

    stream.foreachRDD(foreachFunc)
  }
}