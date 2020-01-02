import java.net.InetSocketAddress

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext, rdd}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume.FlumeUtils

object flumeSql {
  def main(args:Array[String]) {
    println("创建Spark配置")
    val conf = new SparkConf().setAppName("flume sql").setMaster("local[4]")
    val sparkContext  = new SparkContext(conf)
    val ctx = new StreamingContext(sparkContext, Seconds(10))
    val sqlCtx = new SQLContext(sparkContext)
    val addresses = new Array[InetSocketAddress](1)
    addresses(0) = new InetSocketAddress("localhost", 4949)
    val flumeStream = FlumeUtils.createPollingStream(ctx, addresses, StorageLevel.MEMORY_AND_DISK_SER_2, 1000, 1)
    // flume 输入的每一行都是一个json object的字符串表示
    val newDStream = flumeStream.flatMap { x => new String(x.event.getBody.array()) }
    newDStream.foreachRDD((rdd: RDD[Char]) => {
      println("打印输入文本")
      val a = rdd.collect()
      println(a)
    })
    val wStream = newDStream.window(Seconds(40),Seconds(20))
    wStream.foreachRDD(rdd=>{

    })
    ctx.start()
    ctx.awaitTermination()
  }
}
