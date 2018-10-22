import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io._
import org.apache.hadoop.mapred.{JobConf, TextOutputFormat}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object dStreamFile extends App {
  val conf = new SparkConf().setAppName("log persist").setMaster("local[4]")
  val ctx = new StreamingContext(conf, Seconds(10))
  val data = ctx.textFileStream("file:///home/a/testStreamDir")
  val flatData = data.flatMap(_.split(" "))

  val words = flatData.map((_, 1))
  val countEachWord = words.reduceByKey(_ + _)

  //data.saveAsTextFiles("/home/a/resultDir/data-", ".txt") 本地用户执行这一句
  val hConf = new JobConf(new Configuration())
  val oldClassOutput = classOf[TextOutputFormat[Text, Text]]
  countEachWord.saveAsHadoopFiles(//hdfs用户
    "hdfs://localhost:9000/myResult/data-",
    "",
    classOf[Text],
    classOf[Text],
    oldClassOutput,
    hConf)
  val newClassOutput = classOf[org.apache.hadoop.mapreduce.lib.output.TextOutputFormat[Text,Text]]
  countEachWord.saveAsNewAPIHadoopFiles("hdfs://localhost:9000/myNewResult/data-",
    "",
    classOf[Text],
    classOf[Text],
    newClassOutput,
    hConf)
  //下面一句会在第二个文件开始抛出异常
  countEachWord.saveAsObjectFiles("hdfs://localhost:9000/myObject12/data-", "")
  countEachWord.print()
  countEachWord.foreachRDD(d=>d.foreach(tup=>System.out.println("Key="+tup._1+", Value="+tup._2)))
  ctx.start()
  ctx.awaitTermination()
}