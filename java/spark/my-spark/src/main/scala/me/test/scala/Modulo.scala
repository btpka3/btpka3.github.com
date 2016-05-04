package me.test.scala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zll on 16-5-4.
  */
object Modulo {

  def main(args: Array[String]): Unit = {


    val s = """{"level":24,"modu":"2","map":["01010","10100","01101","11101","00111","01011"],"pieces":["XX.,.XX","X,X,X,X","X.XX,XXX.,.X..,.XX.","XXX,.X.","XXX..,X.XXX","..X.,XXXX",".X,XX,XX,X.",".XXX,.XX.,..X.,XXX.,..X.","...X,...X,XXXX","..X,XXX","..XX.,XXXXX,XX..X","X..,X..,XXX,.X."]}"""
    println("-------------------------99")
    println(s)
    println("-------------------------99")
  }


  def main1(): Unit = {
    val conf = new SparkConf()
      .setAppName("btpka3")
      .setMaster("local[2]")

    val sc = new SparkContext(conf)

  }

}
