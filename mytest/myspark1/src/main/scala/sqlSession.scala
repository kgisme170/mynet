import org.apache.spark.sql.SparkSession
object sqlSession extends App {
  def fromJson(): Unit ={
    val jsonFile = spark.read.json("test2.json")
    jsonFile.createOrReplaceTempView("gender")
    val genderRDD = spark.sql("select * from gender")
    genderRDD.show()
    genderRDD.printSchema()
  }
  val spark = SparkSession.builder().appName("sqlSession").getOrCreate()
  val data = spark.sqlContext.sql("show databases")//select * from z")
  data.show()
  data.printSchema()
}