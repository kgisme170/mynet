import org.apache.spark.{SparkConf, SparkContext}
object jsoncsv extends App {
  def testjson: Unit = {
    case class Address(line1: String, city: String, state: String, zip: String) {
      override def toString = s"Address(line1=$line1, city=$city, state=$state, zip=$zip)"
    }
    case class Person(firstName: String, lastName: String, address: List[Address]) {
      override def toString = s"Person(firstName=$firstName, lastName=$lastName, address=$address)"
    }
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
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

  def testcsv: Unit = {
    import java.io.StringReader
    import au.com.bytecode.opencsv.CSVReader
    case class myData(index: String, title: String, content: String) {
      override def toString = s"Data(index=$index, title=$title, content=$content)"
    }
    val input = sc.textFile("sample.csv")
    val result = input.map { line =>
      val reader = new CSVReader(new StringReader(line))
      reader.readNext()
    }
    for (res <- result) {
      for (re <- res)
        println(re)
    }

    val _ = sc.wholeTextFiles("sample.csv")
  }

  def testJackson(): Unit = {
    import com.fasterxml.jackson.module.scala.DefaultScalaModule
    import com.fasterxml.jackson.module.scala.experimental.DefaultRequiredAnnotationIntrospector
    import com.fasterxml.jackson.databind
    /*
    case class Person(name:String, lovePandas:Boolean)
    val input = sc.textFile("test2.json")
    val result = input.flatMap(data=>{
      try{
        Some(mapper.readValue(data,classOf[Person]))
      }catch{
        case _:Exception=>None
      }
    })*/
  }

  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  testcsv
}