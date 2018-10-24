import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object sqlProgrammaticSchema {

  case class Person(name: String, age: Long)

  case class S(No_Of_Emp: Long, Name: String, No_Of_Supervisors: Long)

  case class Person2(name: String, age: Long, num: Long)

  val conf = new SparkConf().setAppName("spark sql")
    .set("spark.sql.warehouse.dir", System.getProperty("user.dir"))
    .setMaster("local[4]");
  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()

  import spark.implicits._

  val caseClass = Seq(Person("Me", 2)).toDS()
  caseClass.show()
  Seq(1, 2, 3).toDS().map(_ + 1).show()
  //可以换顺序
  val df = spark.read.option("multiLine", true).json("my.json").as[S]
  df.show()
  println("rdd to df")
  val df2 = spark.sparkContext.textFile("my.txt")
    .map(_.split(","))
    .map(a => Person2(a(0), a(1).trim.toInt, a(2).trim.toInt))
    .toDF()
  df2.map(name => "Name:" + name).show()
  val rowRdd = spark.sparkContext.textFile("my.txt")
    .map(_.split(","))
    .map(a => Row(a(0), a(1).trim, a(2).trim))
  val schema = StructType("a b c".split(" ")
    .map(f => StructField(f, StringType, nullable = true)))
  val df3 = spark.createDataFrame(rowRdd, schema)
  df3.createOrReplaceTempView("people")
  spark.sql("select * from people").show()

  /* Create an RDD
  val peopleRDD = spark.sparkContext.textFile("my.txt")
  val schemaString = "name age no"// The schema is encoded in a string
  val fields = schemaString.split(" ")// Generate the schema based on the string of schema
    .map(fieldName => StructField(fieldName, StringType, nullable = true))
  val schema = StructType(fields)
  val rowRDD = peopleRDD// Convert records of the RDD (people) to Rows
    .map(_.split(","))
    .map(attributes => Row(attributes(0), attributes(1).trim, attributes(2).trim))
  val peopleDF = spark.createDataFrame(rowRDD, schema)// Apply the schema to the RDD
  peopleDF.createOrReplaceTempView("people")// Creates a temporary view using the DataFrame
  val results = spark.sql("SELECT name FROM people")// SQL can be run over a temporary view created using DataFrames

  // The results of SQL queries are DataFrames and support all the normal RDD operations
  // The columns of a row in the result can be accessed by field index or by field name
  results.map(attributes => "Name: " + attributes(0)).show()*/
}