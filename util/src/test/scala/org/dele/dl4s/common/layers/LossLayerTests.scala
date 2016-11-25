package org.dele.dl4s.common.layers

import org.nd4j.linalg.api.ndarray.INDArray
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dele on 2016-11-26.
  */
class LossLayerTests extends FlatSpec with Matchers {
  "Smoke test" should "be ok" in {
    val lf:(INDArray,INDArray)=>Double = LossLayers.EcuLossLayer.loss
    lf shouldNot be(null)
  }
}
