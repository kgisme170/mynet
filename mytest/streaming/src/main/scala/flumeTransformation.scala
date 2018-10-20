import java.net.InetSocketAddress
import java.util.regex.Pattern

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object flumeTransformation {
  def main(args: Array[String]): Unit = {
    println("创建Spark配置")
    val conf = new SparkConf().setAppName("Apache log transformer").setMaster("local[4]")
    val ctx = new StreamingContext(conf, Seconds(10))
    val addresses = new Array[InetSocketAddress](1)
    addresses(0) = new InetSocketAddress("localhost", 4949)
    val flumeStream = FlumeUtils.createPollingStream(ctx, addresses, StorageLevel.MEMORY_AND_DISK_SER_2, 1000, 1)

    val newDStream = flumeStream.flatMap { x => transformLogData(new String(x.event.getBody.array())) }
    //newDStream.filter(x => x._1.equals("method") && x._2.contains("GET")).count().print()
    //newDStream.filter(x => x._1.contains("request")).map(x => (x._2, 1)).reduceByKey(_ + _).print(100)
    printLogValues(newDStream, ctx)
    println("打印 window 结果")
    val wStream = newDStream.window(Seconds(40), Seconds(20))
    val localhost = wStream.filter(x => x._1.contains("IP")).map(x => (x._2, 1))
    localhost.reduceByKey(_ + _).print(100)

    println("打印 reduceByKeyAndWindow 结果")
    val localhost1 = newDStream.filter(x => x._1.contains("IP")).map(x => (x._2, 1))
    localhost1.reduceByKeyAndWindow((x: Int, y: Int) => x + y, Seconds(40), Seconds(20)).print(100)

    println("打印 groupByKeyAndWindow 结果")
    val localhost2 = newDStream.filter(x => x._1.contains("IP")).map(x => (x._2, 1))
    localhost2.groupByKeyAndWindow(Seconds(40), Seconds(20)).print(100)

    ctx.start()
    ctx.awaitTermination()
  }

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

  def transformLogData(logLine: String): Map[String, String] = {
    return Map[String, String](
      ("IP" -> "localhost")
    )
    //正则表达式的问题未解决，不重要
    val LOG_ENTRY_PATTERN =
      """^(\S+) (\S+) (\S+)
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
}