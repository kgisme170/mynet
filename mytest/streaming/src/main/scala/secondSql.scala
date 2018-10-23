import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.types._

import org.apache.spark.sql.types.StructType
object secondSql extends App {
  val conf = new SparkConf().setAppName("spark sql")
    .set("spark.sql.warehouse.dir", System.getProperty("user.dir"))
    .setMaster("local[4]");
  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()

  case class Person(name: String, age: Long)

  import spark.implicits._
/*
  val caseClass = Seq(Person("Me", 2)).toDS()
  caseClass.show()
  Seq(1, 2, 3).toDS().map(_ + 1).show()

  case class S(No_Of_Emp: Long, Name: String, No_Of_Supervisors: Long)

  //可以换顺序
  val df = spark.read.json("my.json").as[S]
  df.show()
  println("rdd to df")

  case class Person2(name: String, age: Long, num: Long)

  val df2 = spark.sparkContext.textFile("my.txt")
    .map(_.split(","))
    .map(a => Person2(a(0), a(1).trim.toInt, a(2).trim.toInt))
    .toDF()
  df2.map(name => "Name:" + name).show()*/
  val rowRdd = spark.sparkContext.textFile("my.txt")
    .map(_.split(","))
    .map(a => Row(a(0), a(1).trim.toInt, a(2).trim.toInt))
  val schema = StructType("a b c".split(" ")
    .map(f => StructField(f, StringType, nullable = true)))
  spark.createDataFrame(rowRdd, schema).createOrReplaceTempView("people")
  spark.sql("select * from people").show()
}