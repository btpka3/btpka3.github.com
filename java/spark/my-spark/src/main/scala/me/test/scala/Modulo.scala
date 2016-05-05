package me.test.scala

import breeze.linalg.{DenseMatrix, Matrix}
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * Created by zll on 16-5-4.
  */
object Modulo {
  def main(args: Array[String]): Unit = {
    //println(mapToMatrix(Array("01010", "10100", "01101", "11101", "00111", "01011")))
    //    val arr = Array("XX.,.XX", "X,X,X,X", "X.XX,XXX.,.X..,.XX.", "XXX,.X.", "XXX..,X.XXX", "..X.,XXXX", ".X,XX,XX,X.", ".XXX,.XX.,..X.,XXX.,..X.", "...X,...X,XXXX", "..X,XXX", "..XX.,XXXXX,XX..X", "X..,X..,XXX,.X.")
    //    for (a <- arr) {
    //      println("------------------------------------")
    //      println(pieceToMatrix(a))
    //    }


    val nums = 1 :: (2 :: (3 :: (4 :: Nil)))
    println(nums)
    println("111" :: "222" :: "333" :: Nil)
    var z: List[String] = List()
    //    z += "ccc"
    println(z)
    var z1 = new ListBuffer[String]()
    z1 += "ccc"
    z1 += "ddd"
    println(z1)




    val fruit1 = "apples" :: ("oranges" :: ("pears" :: Nil))
    val fruit2 = "mangoes" :: ("banana" :: Nil)

    var ab = fruit1 ::: fruit2
    println(ab.::("ccc").::("ddd"))


  }


  def mapToMatrix(strArr: Array[String]): Matrix[Int] = {
    // http://stackoverflow.com/questions/31064753/how-pass-scala-array-into-scala-vararg-method
    //var strArr = Array("01010", "10100", "01101", "11101", "00111", "01011")
    val a = strArr.map(str => str.map(x => x - 48)) // x => x.toString.toInt
    DenseMatrix(a: _*)
  }

  def pieceToMatrix(str: String): Matrix[Int] = {
    val a = str.split(",").map(str => str.map(x => if ('X' == x) 1 else 0))
    DenseMatrix(a: _*)
  }

  def calcPiecePos(map: Matrix[Int], piece: Matrix[Int]): Array[(Int, Int)] = {
    val arr = new Array[(Int, Int)](0)
    arr
  }


  def main1(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("btpka3")
      .setMaster("local[2]")

    val sc = new SparkContext(conf)
    val data = Array(1, 2, 3, 4, 5)
    val distData = sc.parallelize(data)
    val d = distData.flatMap { p =>
      Array(p + 100, p + 200)
    }
    println(d.collect().toList)
  }


  def main2(args: Array[String]): Unit = {


    val s = """{"level":24,"modu":"2","map":["01010","10100","01101","11101","00111","01011"],"pieces":["XX.,.XX","X,X,X,X","X.XX,XXX.,.X..,.XX.","XXX,.X.","XXX..,X.XXX","..X.,XXXX",".X,XX,XX,X.",".XXX,.XX.,..X.,XXX.,..X.","...X,...X,XXXX","..X,XXX","..XX.,XXXXX,XX..X","X..,X..,XXX,.X."]}"""
    println("-------------------------99")
    println(s)
    println("-------------------------99")
    //JsonParser
    val mapper = new ObjectMapper()
    val node = mapper.readTree(s)
    println(node.get("modu"))
  }


  def main1(): Unit = {

  }

  /*
  1. 1
    */

}
