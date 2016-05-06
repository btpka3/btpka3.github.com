package me

import scala.collection.mutable.ListBuffer
import breeze.linalg.Matrix

/**
  * Created by zll on 16-5-7.
  */
package object test {
  type ModuResult = Array[(Matrix[Int], (Int, Int))]
  type ModuResults = ListBuffer[ModuResult]
}
