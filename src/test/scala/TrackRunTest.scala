package com.nicta
package trackfunction

import scalaz.scalacheck.ScalazProperties._
import scalaz.std.anyVal._

class TrackRunTest extends Spec {

  type TrackRunIntInt[α] = TrackRun[Int, Int, α]

  checkAll(comonad.laws[TrackRunIntInt])
  checkAll(monad.laws[TrackRunIntInt])
  checkAll(order.laws[TrackRun[Int, Int, Int]])
  checkAll(equal.laws[TrackRun[Int, Int, Int]])

}

