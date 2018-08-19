import java.sql.{DriverManager, ResultSet}

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object jdbcMysql extends App {
  /*val spark = SparkSession.builder().appName("sqlSession").getOrCreate()
  val ctx = spark.sqlContext
  val sql = "select * from db1.t1"
  val df = ctx.read.format("jdbc").options(Map(
    "driver"->"com.mysql.jdbc.Driver",
    "url"->"jdbc:mysql://localhost:14340",
    "dbtable"->"db1.t1",
    "user"-> "root",
    "password"->"mypassword",
    "lowerBound"->"0",
    "upperBound"-> "1000",
    "fetchSize"->"100"
  )).load()*/

  val conf = new SparkConf().setAppName("mysql").setMaster("local[4]")
  val sc = new SparkContext(conf)

  def createConnection() = {
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    DriverManager.getConnection("jdbc:mysql://localhost:14340/db1")
  }

  //createConnection()
  def extractValues(r: ResultSet) = {
    (r.getInt(1), r.getString(2))
  }

  val data = new JdbcRDD(sc, createConnection, "select * from t1",
    lowerBound = 1,
    upperBound = 3,
    numPartitions = 2,
    mapRow = extractValues)

  println(data.collect().toList)
  /*
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)

  //val sql="select id,region,city,company,name from tb_user_imei"
  //定义mysql信息
  val jdbcDF = sqlContext.read.format("jdbc").options(Map(
      "driver"->"com.mysql.jdbc.Driver",
      "url"->"jdbc:mysql://localhost:14340",
      "dbtable"->"db1.t1",
      "user"-> "root",
      "password"->"mypassword",
      "lowerBound"->"0",
      "upperBound"-> "1000",
      "fetchSize"->"100"
  )).load()
  jdbcDF.show
  jdbcDF.collect().take(20).foreach(println) //终端打印DF中的数据。*/
}