package com.nicta

import scalaz.scalacheck.ScalaCheckBinding._
import org.scalacheck.{Gen, Arbitrary}
import scalaz._

package object trackfunction {

  type Spec = org.specs2.scalaz.Spec

  implicit def smallListArb[A](implicit A: Arbitrary[A]): Arbitrary[List[A]] =
    Arbitrary(Gen.choose(0, 5).flatMap{ n =>
      Gen.listOfN(n, Arbitrary.arbitrary[A])
    })

  implicit def TrackResultArb[A, B](implicit A: Arbitrary[A], B: Arbitrary[B]) =
    Apply[Arbitrary].apply2(A, B)(TrackResult.apply)

  implicit def TrackResultsArb[A, B](implicit A: Arbitrary[List[TrackResult[A, B]]]) =
    Functor[Arbitrary].map(A)(TrackResults.apply)

  implicit def TrackRunArb[A, B, C](implicit A: Arbitrary[A], B: Arbitrary[TrackResults[B, C]]) =
    Apply[Arbitrary].apply2(A, B)(TrackRun.apply)

}

