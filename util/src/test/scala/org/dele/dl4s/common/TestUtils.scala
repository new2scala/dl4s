package org.dele.dl4s.common

/**
  * Created by jiaji on 11/28/2016.
  */
object TestUtils {
  def doubleEqual(d1:Double, d2:Double)(implicit tolerance:Double):Boolean = math.abs(d1-d2) <= tolerance
}
