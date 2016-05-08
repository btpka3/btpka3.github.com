package me

import scala.collection.mutable.ListBuffer
import breeze.linalg.Matrix

/**
  * Created by zll on 16-5-7.
  */
package object test {
  type PiecePos = (Matrix[Int], (Int, Int))
  type ModuResult = Array[PiecePos]
  type ModuResults = ListBuffer[ModuResult]
}
