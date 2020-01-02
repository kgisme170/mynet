import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
object partitionCombine extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  println("test00==================================")
  val input = sc.parallelize(List(1, 2, 3, 4))
  val result = input.map(x => x * x)
  println(result.collect().mkString(","))
  println("test01==================================")

  val words = Array("one", "two", "three", "four", "two", "two", "one")
  val wordsRDD = sc.parallelize(words).map(word => (word, 1))
  val wordsReduce = wordsRDD.reduceByKey(_ + _).collect().foreach(println)
  println("test02==================================")
  val wordsReduceLocal = wordsRDD.reduceByKeyLocally(_ + _).foreach(println)
  println("test03==================================")
  val wordsGroup = wordsRDD.groupByKey().map(w => (w._1, w._2.sum)).foreach(println)
  println("test04==================================")
  wordsRDD.groupByKey().foreach(println)
  println("test05==================================")
  var rdd1 = sc.parallelize(Array(("A", 9.0), ("A", 2.0), ("B", 1.0), ("B", 2.0), ("C", 1.0))).partitionBy(new HashPartitioner(3))
  rdd1.foreachPartition(p => println(p.length))
  println("test06==================================")
  rdd1.foreachPartition(p => p.foreach(i => println(i._2)))
  println("test07==================================")

  type cType = (Int, Double)
  val cb = rdd1.combineByKey(
    createCombiner = c => (1, c),
    mergeValue = (c: cType, v) => (c._1 + 1, c._2 + v),
    mergeCombiners = (c: cType, c2: cType) => (c._1 + c2._1, c._2 + c2._2),
    numPartitions = rdd1.partitions.size
  )
  cb.foreach(println)
  println("test08==================================")
  cb.map { case (name, (num, score)) => (name, score / num) }.foreach(println)
  println("test09==================================")
}