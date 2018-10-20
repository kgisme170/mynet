import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ch2 extends App {
  val streamCtx = new StreamingContext(sparkConf, Seconds(2))
  println("创建streaming context")
  val lines = streamCtx.socketTextStream("localhost", 9087)
  val words = lines.flatMap(x => x.split(" "))
  val pairs = words.map(w => (w, 1))
  val wordCounts = pairs.reduceByKey(_ + _)
  var sparkConf = new SparkConf()
    .setAppName("myapp")
    .setMaster("local[4]")
    //.setJars(Array("."))//会在当前目录寻找一个叫做/jars的目录，然后继续找执行的jar包
    .set("spark.executor.memory", "1g")
  wordCounts.print(20)
  streamCtx.start()
  streamCtx.awaitTermination()
}
