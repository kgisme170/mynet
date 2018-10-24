import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.StructType
object sqlDfRf {
  val conf = new SparkConf().setAppName("spark sql")
    .set("spark.sql.warehouse.dir", System.getProperty("user.dir"))
    .setMaster("local[4]");
  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()
  val pFile = "/tmp/file1.parquet"

  case class Person(name: String, age: Long)

  case class S(No_Of_Emp: Long, Name: String, No_Of_Supervisors: Long)

  case class Person2(name: String, age: Long, num: Long)

  import spark.implicits._
  object myAverage extends UserDefinedAggregateFunction{
    override def inputSchema: StructType = StructType(StructField("inputColumn",LongType)::Nil)

    override def bufferSchema: StructType = StructType(StructField("sum",LongType)::StructField("count",LongType)::Nil)

    override def dataType: DataType = DoubleType

    override def deterministic: Boolean = true

    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      println("initialize")
      buffer(0)=0
      buffer(1)=0
    }

    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      println("update")
      buffer(0) = buffer.getLong(0) + input.getLong(0)
      buffer(1) = buffer.getLong(1) + input.getLong(1)
    }

    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      println("merge")
      buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
      buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
    }

    override def evaluate(buffer: Row): Any = {
      println("evaluate")
      buffer.getLong(0).toDouble / buffer.getLong(1)
    }
  }
  def main(args:Array[String]) {
    //typeUnsafeAggregation()
    object MyAverage extends UserDefinedAggregateFunction {
      def inputSchema: StructType = StructType(StructField("inputColumn", LongType) :: Nil)
      def bufferSchema: StructType = {
        StructType(StructField("sum", LongType) :: StructField("count", LongType) :: Nil)
      }
      def dataType: DataType = DoubleType
      def deterministic: Boolean = true

      def initialize(buffer: MutableAggregationBuffer): Unit = {
        buffer(0) = 0L
        buffer(1) = 0L
      }
      def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
        if (!input.isNullAt(0)) {
          buffer(0) = buffer.getLong(0) + input.getLong(0)
          buffer(1) = buffer.getLong(1) + 1
        }
      }
      def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
        buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
        buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
      }
      def evaluate(buffer: Row): Double = buffer.getLong(0).toDouble / buffer.getLong(1)
    }
    spark.udf.register("myAverage", MyAverage)

    val df = spark.read.json("employees.json")
    df.createOrReplaceTempView("employees")
    df.show()
    val result = spark.sql("SELECT myAverage(salary) as average_salary FROM employees")
    result.show()
  }
  def typeUnsafeAggregation(): Unit ={
    spark.udf.register("myAverage", myAverage)

    val df = spark.read.option("multiLine", true).json("employees.json")
    df.createOrReplaceTempView("people")
    //spark.sql("select No_Of_Supervisors as avg from people").show()//OK
    spark.sql("select myAverage(salary) as avg from people").show()
  }
  def testParquet() {
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
  }

  def testProgrammaticSchema() {
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
    df3.select("a", "b", "c").write.save(pFile)
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
}