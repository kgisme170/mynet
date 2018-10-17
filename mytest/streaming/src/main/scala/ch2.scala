import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ch2 extends App{
  var sparkConf = new SparkConf()
    .setAppName("myapp")
    .setMaster("local[4]")
    //.setJars(Array("."))
    .set("spark.executor.memory", "1g")
  println("创建streaming context")
  val streamCtx = new StreamingContext(sparkConf, Seconds(2))
  val lines = streamCtx.socketTextStream("localhost",9087)
  val words = lines.flatMap(x=>x.split(" "))
  val pairs = words.map(w=>(w,1))
  val wordCounts = pairs.reduceByKey(_ + _)
  wordCounts.print(20)
  streamCtx.start()
  streamCtx.awaitTermination()
}
