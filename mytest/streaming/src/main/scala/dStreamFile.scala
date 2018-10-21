import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object dStreamFile extends App {
  val conf = new SparkConf().setAppName("log persist").setMaster("local[4]")
  val ctx = new StreamingContext(conf, Seconds(10))
  val data = ctx.textFileStream("file:///home/a/testStreamDir")
  val flatData = data.flatMap(_.split(" "))

  val words = flatData.map((_, 1))
  val countEachWord = words.reduceByKey(_ + _)

  countEachWord.print()
  ctx.start()
  ctx.awaitTermination()
}
