import org.apache.spark.{SparkConf,SparkContext}

object accumulator extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val file = sc.textFile("emptyLines.txt")
  //val blankLines = sc.accumulator(0)
  var blankLines = 0
  val callSigns = file.flatMap(line => {
    if (line == "") {
      blankLines += 1
    }
    line.split(" ")
  })
  callSigns.foreach(println)
  println(blankLines)

  val b = sc.broadcast(file.collect)
  b.value.foreach(println)

  val data = sc.parallelize(List(1,3,4,1,9))
  val stats = data.stats()
  println(stats.stdev)
  println(stats.mean)
}