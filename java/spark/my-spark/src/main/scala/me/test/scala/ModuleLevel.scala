package me.test.scala

import scala.beans.BeanProperty

/**
  * Created by zll on 16-5-5.
  */
class ModuleLevel extends Serializable {
  @BeanProperty var level: Int = 0
  @BeanProperty var modu: Int = 0
  @BeanProperty var map: Array[String] = null
  @BeanProperty val pieces: Array[String] = null

  override def toString = s"ModuleLevel: (level : $level, modu : $modu, map : Array(${map.mkString("; ")}), pieces : Array(${pieces.mkString("; ")}))"
}
