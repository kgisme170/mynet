import org.apache.spark.SparkConf
import org.apache.spark.sql._
import java.io._
object sqlDfRf {
  def mergeSchema(): Unit ={
    val conf = new SparkConf().setAppName("spark sql")
      .set("spark.sql.warehouse.dir", System.getProperty("user.dir"))
      .setMaster("local[4]");
    val spark = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()
    import spark.implicits._
    val squaresDF = spark.sparkContext.makeRDD(1 to 5).map(i => (i, i * i)).toDF("value", "square")
    squaresDF.write.parquet("data/test_table/key=1")

    // Create another DataFrame in a new partition directory,
    // adding a new column and dropping an existing column
    val cubesDF = spark.sparkContext.makeRDD(6 to 10).map(i => (i, i * i * i)).toDF("value", "cube")
    cubesDF.write.parquet("data/test_table/key=2")

    // Read the partitioned table
    val mergedDF = spark.read.option("mergeSchema", "true").parquet("data/test_table")
    mergedDF.printSchema()
  }
  def main(args: Array[String]) {
    val warehouseLocation = new File("spark-warehouse").getAbsolutePath

    val spark = SparkSession
      .builder()
      .appName("Spark Hive Example")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import spark.sql

    sql("CREATE TABLE IF NOT EXISTS src (value STRING) USING hive")
    sql("LOAD DATA LOCAL INPATH 'kv1.txt' INTO TABLE src")
    println("result 1")
    sql("SELECT * FROM src").show()

    val sqlDF = sql("SELECT value FROM src")
    println("result 2")
    sqlDF.map {case Row(value: String) => s"Value: $value"}.show()
    val df = spark.table("src")
    df.write.mode(SaveMode.Overwrite).saveAsTable("hive_records")
    println("result 3")
    sql("SELECT * FROM hive_records").show()

    // Prepare a Parquet data directory
    val dataDir = "/tmp/parquet_data3"
    spark.range(10).write.parquet(dataDir)
    sql(s"CREATE EXTERNAL TABLE hive_ints3(value string) STORED AS PARQUET LOCATION '$dataDir'")
    println("result 4")
    sql("SELECT * FROM hive_ints3").show()//empty

    spark.stop()
  }
}