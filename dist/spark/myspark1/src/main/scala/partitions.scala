import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
object partitions extends App {
  val conf = new SparkConf().setMaster("local[3]").setAppName("My App")
  val sc = new SparkContext(conf)
  //var pairRdd = sc.parallelize(List((1,1), (1,2), (2,3), (2,4), (3,5), (3,6),(4,7), (4,8),(5,9), (5,10)))
  //pairRdd.partitionBy(new HashPartitioner(3))
  //pairRdd.mapPartitions{(x:Int,y:Int)=>(x+1,y+1)}
  //pairRdd.collect.foreach(println)
  //pairRdd.saveAsTextFile("output")
  var rdd1 = sc.makeRDD(1 to 10, 3)
  var rdd2 = rdd1.mapPartitions { data => {
    var result = List[Int]()
    var sum = 0
    while (data.hasNext) {
      sum += data.next()
    }
    result.::(sum).iterator
  }
  }
  println("before collect")
  rdd2.collect.foreach(println)
  println("after collect")

  val rdd3 = rdd1.mapPartitionsWithIndex { (index, data) => {
    val result = List[String]()
    var sum = 0
    while (data.hasNext) {
      sum += data.next()
    }
    result.::(index.toString + '|' + sum).iterator
  }
  }

  println("before rdd3 collect")
  rdd3.collect.foreach(println)
}