package org.dele.dl4s.common.nodes

/**
  * Created by jiaji on 11/28/2016.
  */
trait TNNode {
  // B-diagram: f(x) compute f, _f(x) compute derivative
  def f(x:Double):NNodeResult
  //def _f(x:Double):Double
}

case class NNodeResult(f:Double, _f:Double)

object NNodes {
  import CostFuncs._

  class LinearFNode(val gradient:Double, val b:Double) extends TNNode {
    override // B-diagram: f(x) compute f, _f(x) compute derivative
    def f(x: Double): NNodeResult = NNodeResult(gradient*x + b, gradient)

    //override def _f(x: Double): Double = gradient
  }

  class EculFNode(val t:Double) extends TNNode {
    override // B-diagram: f(x) compute f, _f(x) compute derivative
    def f(x: Double): NNodeResult = NNodeResult(ecul(x, t), x-t)

    //override def _f(x: Double): Double = gradient
  }

  object Sigmoid extends TNNode {

    private def _f(x: Double): Double = 1.0 / (1 + math.exp(x))
    override def f(x: Double): NNodeResult = NNodeResult(_f(x), _f(x)*(1-_f(x)))
  }
}