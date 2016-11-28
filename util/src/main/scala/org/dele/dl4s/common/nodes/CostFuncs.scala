package org.dele.dl4s.common.nodes

/**
  * Created by jiaji on 11/28/2016.
  */
object CostFuncs {
  def ecul(y:Double, t:Double):Double = {
    val d = y - t
    d*d/2
  }
}
