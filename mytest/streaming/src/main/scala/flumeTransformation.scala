import java.net.InetSocketAddress
import java.util.regex.Pattern

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object flumeTransformation {
  def printLogValues(stream: DStream[(String, String)], ctx: StreamingContext): Unit = {
    stream.foreachRDD((rdd: RDD[(String, String)]) => {
      val a = rdd.collect()
      println("开始打印")
      for (dataMap <- a.array) {
        print(dataMap._1, "-----", dataMap._2)
      }
      println("结束打印")
    })
  }

  def main(args: Array[String]): Unit = {
    println("创建Spark配置")
    val conf = new SparkConf().setAppName("Apache log transformer").setMaster("local[4]")
    val ctx = new StreamingContext(conf, Seconds(10))
    val addresses = new Array[InetSocketAddress](1)
    addresses(0) = new InetSocketAddress("localhost", 4949)
    val flumeStream = FlumeUtils.createPollingStream(ctx, addresses, StorageLevel.MEMORY_AND_DISK_SER_2, 1000, 1)
    val newDStream = flumeStream.flatMap { x => transformLogData(new String(x.event.getBody.array())) }
    executeTransformations(newDStream, ctx)
    ctx.start()
    ctx.awaitTermination()
  }

  def transformLogData(logLine: String): Map[String, String] = {
    val LOG_ENTRY_PATTERN ="""^(\S+) (\S+) (\S+)
      \[([\w:/]+\s[+\-]\d{4})\] "(\S+) (\S+) (\S+)" (\d{3})
      (\S+)"""
    val PATTERN = Pattern.compile(LOG_ENTRY_PATTERN)
    val m = PATTERN.matcher(logLine)
    if (!m.find()) {
      System.out.println("不能处理 log line" + logLine)
    }
    return Map[String, String](
        ("IP" -> m.group(1)),
        ("client" -> m.group(2)),
        ("user" -> m.group(3)),
        ("date" -> m.group(4)),
        ("method" -> m.group(5)),
        ("request" -> m.group(6)),
        ("protocol" -> m.group(7)),
        ("respCode" -> m.group(8)),
        ("size" -> m.group(9))
    )
  }

  def executeTransformations(dStream: DStream[(String, String)], ctx: StreamingContext): Unit = {
    printLogValues(dStream, ctx)
    dStream.filter(x => x._1.equals("method") && x._2.contains("GET")).count().print()
    dStream.filter(x => x._1.contains("request")).map(x => (x._2, 1)).reduceByKey(_ + _).print(100)
  }
}