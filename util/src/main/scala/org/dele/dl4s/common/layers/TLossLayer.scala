package org.dele.dl4s.common.layers

import org.nd4j.linalg.api.ndarray.INDArray

/**
  * Created by dele on 2016-11-26.
  */
trait TLossLayer {
  def loss(est:INDArray, target:INDArray):Double
}

object LossLayers {
  object EcuLossLayer extends TLossLayer {
    override def loss(est: INDArray, target: INDArray): Double = {
      1.0
    }
  }
}
