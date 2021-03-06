import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object sqlParquet {
  val conf = new SparkConf().setAppName("spark sql")
    .set("spark.sql.warehouse.dir", System.getProperty("user.dir"))
    .setMaster("local[4]");
  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()
  val pFile = "/tmp/file1.parquet"
  val userDF = spark.read.load(pFile)
  userDF.select("a", "b", "c").show()
  spark.sql("select a,b from parquet.`/tmp/file1.parquet`").show()
  val df = spark.read.parquet(pFile)
  df.createOrReplaceTempView("my")
  spark.sql("select b,c from my").show()
  userDF.toJSON.take(3).foreach(println)
  /*{"a":"B","b":"13","c":"6"}
  {"a":"C","b":"3","c":"4"}
  {"a":"A","b":"1","c":"2"}
  */
  df.select("a", "b", "c").write.save(pFile)
  val df2 = spark.read.option("multiLine", true).json("employees.json")
  spark.sql("create database people")
  df2.write.bucketBy(42, "name").sortBy("salary").saveAsTable("people.bucket")
  df2.write.partitionBy("salary").format("parquet").save("4.parquet")
  println("1st result")
  spark.sql("select * from parquet.`people.db/bucket`").show()
  println("2nd result")
  spark.sql("select * from parquet.`4.parquet`").show()
}