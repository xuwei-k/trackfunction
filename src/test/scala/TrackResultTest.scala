package com.nicta
package trackfunction

import scalaz.scalacheck.ScalazProperties._
import scalaz.std.anyVal._

class TrackResultTest extends Spec {

  type TrackResultInt[α] = TrackResult[Int, α]

  checkAll(traverse.laws[TrackResultInt])
  checkAll(comonad.laws[TrackResultInt])
  checkAll(monad.laws[TrackResultInt])
  checkAll(order.laws[TrackResult[Int, Int]])
  checkAll(equal.laws[TrackResult[Int, Int]])

}

