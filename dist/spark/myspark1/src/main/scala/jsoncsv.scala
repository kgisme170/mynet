import org.apache.spark.{SparkConf, SparkContext}
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read,write}
case class Address(line1: String, city: String, state: String, zip: String) {
  override def toString = s"Address(line1=$line1, city=$city, state=$state, zip=$zip)"
}
case class Person(firstName:String, lastName:String, address:List[Address]){
  override def toString = s"Person(firstName=$firstName, lastName=$lastName, address=$address)"
}
object jsoncsv extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)

  val inputJson = "test.json"
  val inputFile = sc.textFile(inputJson)
  val inputRDD = inputFile.map { record =>
    implicit val formats = DefaultFormats
    parse(record).extract[Person]
  }
  inputRDD.collect()
  inputRDD.foreach(println)
  inputRDD.saveAsTextFile("myResult")
}