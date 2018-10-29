import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext
object hiveCtx extends App {
  def fromJson(): Unit = {
    val lp = ctx.jsonFile("test2.json")
    lp.registerTempTable("user");
    ctx.udf.register("strlen", (_: String).length)
    val schemaRDD = ctx.sql("select strlen(name), lovePandas from user");
    schemaRDD.columns.foreach(println)
    schemaRDD.show()
    schemaRDD.printSchema()
  }

  def fromHiveTable(): Unit = {
    val rows = ctx.sql("select * from z")
    rows.show()
    rows.printSchema()
  }

  def fromLocal(): Unit = {
    case class person(h: String, f: String)
    val rdd = context.parallelize(List(person("hi", "there"), person("ok", "yes")))
    val sdd = ctx.applySchema(rdd, classOf[person])
    sdd.registerTempTable("me")
    val rows = ctx.sql("select * from me")
    rows.printSchema()
  }

  val conf = new SparkConf().setMaster("local").setAppName("My App")
  conf.set("spark.sql.codegen", "true")
  val context = new SparkContext(conf)
  val ctx = new HiveContext(context)
  fromJson()
}