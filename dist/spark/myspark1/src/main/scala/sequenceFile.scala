import org.apache.spark.{SparkConf, SparkContext}
object sequenceFile extends App{
    val data = sc.parallelize(List(("A", 9.0), ("A", 2.0), ("B", 1.0), ("B", 2.0), ("C", 1.0)))
    data.saveAsSequenceFile(outputFile)

    val inData = sc.sequenceFile(outputFile, classOf[Text], classOf[IntWritable])
    .map{case(x,y)=>(x.toString, y.get()}
}