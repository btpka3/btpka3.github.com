package me.test.scala

import breeze.linalg._

import scala.collection.mutable

object A {

  def main(args: Array[String]): Unit = {

    println("-------------------------0")
    println(Array(1, 2, 3).getClass)
    println("01010".map(c => c - 48).toArray.getClass)
    println(DenseMatrix(
      Array(//mutable.WrappedArray
        Array(1, 0, 0, 0, 0),
        Array(0, 1, 0, 0, 0)
      )
    ))
    println(DenseMatrix(
      Array(1, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0),
      Array(0, 0, 1, 0, 0),
      Array(0, 0, 0, 1, 0),
      Array(0, 0, 0, 0, 1)
    ))

    val m = DenseMatrix.zeros[Int](5, 5)
    println("-------------------------2")
    println(m)
    println(DenseMatrix)

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

    println("-------------------------2")
    println(m1 + m1)
    println(m1)

    println("-------------------------2.1")
    println(m2.reshape(2, 8))
    println(m2)

    println("-------------------------2.2")
    m2(::, 2) := 5
    println(m2)

    println("-------------------------2.3")
    println(m2(0, 2))
    println(m2)

    println("-------------------------2.4")
    println(m2(1, ::))
    println(m2)

    println("-------------------------2.5")
    m2(1 to 2, 1 to 2) := 4
    println(m2)

    println("-------------------------3")
    //    println(m1 + m2)
    println(DenseVector.fill(5) {
      5.0
    })



    println("-------------------------4")
    println(DenseMatrix.ones[Int](5, 5))

    println("-------------------------5")
    println(diag(DenseVector(1, 2, 3)))

    println("-------------------------6")
    val m3 = DenseMatrix.eye[Int](4)
    println(m3)
    val m4 = DenseMatrix.eye[Int](2) * 2
    println(m4)
    println("-------------------------6.1")
    val m31 = m3.copy
    m3(2 to 3, 1 to 2) += m4
    println(m3)
    println("-------------------------6.2")
    println(m31)

    println("-------------------------7")
    println(linspace(0, 20, 11).toDenseMatrix)

    println("-------------------------8")

    println("Hello, world!6")
  }

  def main1(args: Array[String]): Unit = {
    //    val conf = new SparkConf()
    //      .setMaster("spark://localhost:7077")
    //      .setAppName("NetworkWordCount")
    //
    //    val ssc = new StreamingContext(conf, Seconds(1))
    //    val lines = ssc.socketTextStream("localhost", 9999)

    println("Hello, world!")
  }

}
