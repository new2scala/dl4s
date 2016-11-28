package org.dele.dl4s.common.nodes

import org.dele.dl4s.common.TestUtils
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by jiaji on 11/28/2016.
  */
class BPTests extends FlatSpec with Matchers {
  import NNodes._
  import CostFuncs._

  /// linear test: y = 0.3x - 0.51
  ///  given input:
  ///    i1:  (0.5, -0.36)
  ///    i2:  (1.7, 0)

  /// simplest test:
  ///   x -- w0 --> [O]
  /// test data:  x = 0.5, y = -0.36, target value for w0 = y/x = 0.72
  private def runOnce(td:(Double,Double), w0:Double, lfac:Double): (Double, Double) = {
    val (x, y) = td
    val cf = new EculFNode(y)

    // forward
    val rc = cf.f(x * w0)

    // backward
    val delta = rc._f
    val deltaw = delta*x

    (-lfac*deltaw, rc.f)
    //println(f"w = $w0%.3f\t c = ${rc.f}%.6f")
    //w0 = w0 + aw
  }

  "Linear test" should "pass" in {
    var w0 = 0.1
    var fac = 0.3
    //val b0 = 0.5

    val td = 0.5 -> -0.36
    (0 to 100).foreach{ _ =>
      val (dw, cost) = runOnce(td, w0, fac)
      println(f"w = $w0%.3f\t c = $cost%.6f")
      w0 = w0 + dw
    }

    /// learning factor btw 4, 8: oscillate to minimum
    println("=============================================================")
    w0 = 0.1
    fac = 5
    (0 to 100).foreach{ _ =>
      val (dw, cost) = runOnce(td, w0, fac)
      println(f"w = $w0%.3f\t c = $cost%.6f")
      w0 = w0 + dw
    }


    /// learning factor 8: oscillate forever
    println("=============================================================")
    w0 = 0.1
    fac = 8
    (0 to 100).foreach{ _ =>
      val (dw, cost) = runOnce(td, w0, fac)
      println(f"w = $w0%.3f\t c = $cost%.6f")
      w0 = w0 + dw
    }

    /// learning factor > 8: explosion
    println("=============================================================")
    w0 = 0.1
    fac = 8.5
    (0 to 100).foreach{ _ =>
      val (dw, cost) = runOnce(td, w0, fac)
      println(f"w = $w0%.3f\t c = $cost%.6f")
      w0 = w0 + dw
    }

  }

  /// simpler test: y = 1.5x - 12
  ///   x --> [H] (x+b0) -- w0 --> [0]
  ///
  val testData = List(
    0.0 -> -12.0,
    8.0 -> 0.0
  )

  private def runOnce2(tds:List[(Double,Double)], w0:Double, b0:Double, lfac:Double): (Double, Double, Double) = {

    val r = tds.map{ td =>
      val (x, y) = td
      val lf = new LinearFNode(1.0, b0)
      val cf = new EculFNode(y)

      // forward
      val lfr = lf.f(x)
      val rc = cf.f(lfr.f * w0)

      // backward
      val delta = rc._f
      val deltaw = delta*lfr.f
      val deltab = delta*w0

      (-lfac*deltaw, -lfac*deltab, rc.f)
    }

    val result = (r.map(_._1).sum / tds.size, r.map(_._2).sum / tds.size, r.map(_._3).sum)
    //(result._1 / tds.size, result._2 / tds.size, result._3)
    //println(f"w = $w0%.3f\t c = ${rc.f}%.6f")
    //w0 = w0 + aw\
    result
  }

  "Linear test 2" should "pass" in {
    var w0 = 0.1
    var b0 = 0.2
    //val b0 = 0.5

    var fac = 0.066
    (0 to 100).foreach { _ =>
      val (dw, db, cost) = runOnce2(testData, w0, b0, fac)
      println(f"w = $w0%.3f\tb = $b0%.3f\tc = $cost%.6f")
      w0 = w0 + dw
      b0 = b0 + db
    }

    // oscillate
    println("=============================================================")
    w0 = 0.1
    b0 = 0.2
    fac = 0.0705
    (0 to 100).foreach { _ =>
      val (dw, db, cost) = runOnce2(testData, w0, b0, fac)
      println(f"w = $w0%.3f\tb = $b0%.3f\tc = $cost%.6f")
      w0 = w0 + dw
      b0 = b0 + db
    }
  }
}
