import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
class DomainNamePartition(numParts: Int)extends Partitioner{
    override def numPartitions: Int = numParts
    override def getPartition(key:Any):Int={
        val domain = new Java.net.URL(key.toString).getHost()
        val code = (domain.hashCode % numPartitions)
        if(code<0){
            code + numPartitions
        }else{
            code
        }
    }
    override def equals(other:Any):Boolean = other match{
        case dnp: DomainNamePartition => dnp.numPartitions == numPartitions
        case _ => false
    }
}
object partitioner extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
}