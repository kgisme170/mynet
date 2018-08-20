
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.Duration
import org.apache.spark.streaming.Seconds
object streamlog extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  conf.set("spark.sql.codegen", "true")
  val ssc = new StreamingContext(conf, Seconds(5))
  val lines = ssc.socketTextStream("localhost", 7000)
  //val infoLines = lines.filter(_.contains("info"))
  lines.print()
  lines.saveAsTextFiles("/Users/x/temp")
  println("保存文件-----------------------------")
  ssc.start()
  ssc.awaitTermination()
}
/*
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object streamlog {
  def main(args: Array[String]) {

    //创建SparkConf对象
    val sparkConf = new SparkConf().setAppName("HdfsWordCount").setMaster("local[2]")
    // Create the context
    //创建StreamingContext对象，与集群进行交互
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // Create the FileInputDStream on the directory and use the
    // stream to count words in new files created
    //如果目录中有新创建的文件，则读取
    val lines = ssc.textFileStream("/Users/x/temp")
    //分割为单词
    val words = lines.flatMap(_.split(" "))
    //统计单词出现次数
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    //打印结果
    wordCounts.print()
    //启动Spark Streaming
    ssc.start()
    //一直运行，除非人为干预再停止
    ssc.awaitTermination()
  }
}
*/