import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object jdbcMysql extends App {
  def useSessionBuilder() {
    val spark = SparkSession.builder().appName("sqlSession").getOrCreate()
    val ctx = spark.sqlContext
    val sql = "select * from t1"
    val df = ctx.read.format("jdbc").options(Map(
      "driver" -> "com.mysql.jdbc.Driver",
      "url" -> "jdbc:mysql://localhost:3306",
      "dbtable" -> "db1.t1",
      "user" -> "root",
      "password" -> "mypassword",
      "lowerBound" -> "0",
      "upperBound" -> "1000",
      "fetchSize" -> "100"
    )).load()
    df.show()
  }
  def useSparkConf() {
    val conf = new SparkConf().setAppName("mysql").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val jdbcDF = sqlContext.read.format("jdbc").options(Map(
      "driver" -> "com.mysql.jdbc.Driver",
      "url" -> "jdbc:mysql://localhost:3306/db1",
      "dbtable" -> "t1",
      "user" -> "root",
      "password" -> "mypassword",
      "lowerBound" -> "0",
      "upperBound" -> "1000",
      "fetchSize" -> "100"
    )).load()
    jdbcDF.show
    jdbcDF.collect().take(20).foreach(println) //终端打印DF中的数据。*/
  }
  useSessionBuilder()
}