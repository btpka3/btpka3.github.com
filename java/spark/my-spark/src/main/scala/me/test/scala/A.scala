import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

object HelloWorld {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setMaster("spark://localhost:7077")
      .setAppName("NetworkWordCount")

    val ssc = new StreamingContext(conf, Seconds(1))
    val lines = ssc.socketTextStream("localhost", 9999)

    println("Hello, world!")
  }
}
