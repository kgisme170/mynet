import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature

import au.com.bytecode.opencsv.CSVReader
import Java.io.StringReader

case class Person(name: String,lovesPandas:Boolean)
val result = input.flatMap(record=>{
    try{
        Some(mapper.readValue(record,classOf[Person]))
    }catch{
        case e:exception=>None
    }
})
val lovers = result.filter(p=>p.lovesPandas)
lovers.map(mapper.writeValueAsString(_)).saveAsTextFile(outputDir)

val input = sc.textFile(inputCsv)
val result = input.map{line=>
    val reader=new CSVReader(new StringReader(line))
    reader.readNext()
}

val inputs  sc.wholeTextFiles(inputDir)
val results = inputs.flatMap{case(_, txt)=>
    val reader = new CSVReader(new StringReader(txt))
    reader.readAll().map(x=>Person(x(0),x(1)))
}
lovers.map(person=>List(person.name, person.favoriteAnimal).toArray)
.mapPartitions{people=>
    val stringWriter = new StringWriter()
    val csvWriter = new CsvWriter(stringWriter)
    csvWriter.writeAll(people.toList)
    Iterator(stringWriter.toString)
}.saveAsTextFile(outDir)