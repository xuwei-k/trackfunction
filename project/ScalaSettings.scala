import sbt._
import Keys._
import Tools.onVersionTask

object ScalaSettings {
  type Sett = Project.Setting[_]

  val Scala211 = "2.11.6"

  lazy val all: Seq[Sett] = Seq(
    scalaVersion := Scala211
  , crossScalaVersions := Seq("2.10.5", Scala211)
  , scalacOptions <++= onVersionTask(
      all = Seq("-deprecation", "-unchecked", "-optimise")
    , on210 = Seq("-Yinline-warnings", "-feature", "-language:implicitConversions", "-language:higherKinds", "-language:postfixOps")
    )
  )
}
