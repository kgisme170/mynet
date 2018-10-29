import org.apache.spark.{SparkConf, SparkContext}
object aggregate extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val data = List(3,1,5,4)
  val result = data.par.aggregate((0,0))(
    seqop = (sum, value) => (sum._1 + value, sum._2 + 1),
    combop = (s1, s2)=>(s1._1 + s2._1, s1._2 + s2._2)
  )
  println(result)

  val data2 = List((1,3),(1,2),(1,4),(2,3),(3,6),(3,8))
  val result2 = sc.parallelize(data2).aggregateByKey(0)(
    seqOp = math.max(_,_),
    combOp = _ + _
  )
  result2.foreach(println)
}