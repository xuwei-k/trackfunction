package com.nicta
package trackfunction

import scalaz.scalacheck.ScalazProperties._
import scalaz.std.anyVal._

class TrackResultsTest extends Spec {

  type TrackResultsInt[α] = TrackResults[Int, α]

  checkAll(traverse.laws[TrackResultsInt])
  checkAll(monad.laws[TrackResultsInt])
  checkAll(order.laws[TrackResults[Int, Int]])
  checkAll(equal.laws[TrackResults[Int, Int]])

}

