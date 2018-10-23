import java.net.InetSocketAddress

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume._
import org.apache.spark.streaming.{Seconds, StreamingContext}

object flumeDualSocket {
  def main(args: Array[String]): Unit = {
    println("创建配置")
    val conf = new SparkConf()
    conf.setAppName("流数据加载").setMaster("local[4]")
    println("接收流context")
    val ctx = new StreamingContext(conf, Seconds(2))
    val addresses = new Array[InetSocketAddress](2)
    addresses(0) = new InetSocketAddress("localhost", 4949)
    addresses(1) = new InetSocketAddress("localhost", 4950)
    val flumeStream = FlumeUtils.createPollingStream(ctx, addresses, StorageLevel.MEMORY_AND_DISK_SER_2, 1000, 1)
    flumeStream.foreachRDD((rdd: RDD[SparkFlumeEvent]) => {
      val array = rdd.collect()
      println("开始打印-----")
      println("Event size=" + array.size)
      for (e <- array) {
        val payLoad = e.event.getBody()
        println(new String(payLoad.array()))
      }
      println("结束打印=====")
    })
    ctx.start()
    ctx.awaitTermination()
  }
}
