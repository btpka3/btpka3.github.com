package me.test.scala

import breeze.linalg.DenseMatrix
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.{Matrices, Matrix}

object ScalaTest {

  def main(args: Array[String]): Unit = {
    val dm: Matrix = Matrices.dense(3, 3, Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0, 7, 8, 9))

    val m4 = DenseMatrix.eye[Int](2) * 2

    println(dm)
    println(dm.getClass)
  }
}
