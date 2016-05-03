package me.test.scala

import breeze.linalg._
import org.apache.spark._
import org.apache.spark.streaming._

object A {

  def main(args: Array[String]): Unit = {

    val m = DenseMatrix.zeros[Int](5, 5)
    println("-------------------------2")
    println(m)

    val m1 = DenseMatrix(
      (1, 0, 0, 0, 0),
      (0, 1, 0, 0, 0),
      (0, 0, 1, 0, 0),
      (0, 0, 0, 1, 0),
      (0, 0, 0, 0, 1)
    )
    val m2 = DenseMatrix(
      (1, 1, 0, 0),
      (0, 1, 1, 0),
      (0, 0, 1, 1),
      (0, 0, 0, 1)
    )
    println("-------------------------1")
    println(m1)

    println("-------------------------1")
    println(m1 + m + m1)

    println("-------------------------1")
    println(m1 + m2)

    println("Hello, world!5")
  }

  def main1(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("spark://localhost:7077")
      .setAppName("NetworkWordCount")

    val ssc = new StreamingContext(conf, Seconds(1))
    val lines = ssc.socketTextStream("localhost", 9999)

    println("Hello, world!")
  }

}
