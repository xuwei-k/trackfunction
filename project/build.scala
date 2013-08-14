import sbt._
import Keys._
import com.typesafe.sbt.pgp.PgpKeys._
import Tools.onVersion

object build extends Build {

  val base = Defaults.defaultSettings ++ ScalaSettings.all ++ seq(
      organization := "NICTA"
    , version := "1.0-SNAPSHOT"
    , scalaVersion := "2.10.2"
  )

  val scalazVersion = "7.0.3"

  val scalaz = "org.scalaz" %% "scalaz-core" % scalazVersion

  val trackfunction = Project(
    id = "trackfunction"
  , base = file(".")
  , settings = base ++ ReplSettings.all ++ PublishSettings.all ++ InfoSettings.all ++ seq(
      name := "trackfunction"
    , libraryDependencies ++= Seq(
        scalaz
      , "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test"
      , "org.typelevel" %% "scalaz-specs2" % "0.1.4" % "test"
      )
    )
  )

  val examples = Project(
    id = "examples"
  , base = file("examples")
  , dependencies = Seq(trackfunction)
  , settings = base ++ seq(
      name := "trackfunction-examples"
    , fork in run := true
    , libraryDependencies ++= Seq(scalaz)
    , javaOptions in run <++= (fullClasspath in Runtime) map { cp => Seq("-cp", sbt.Build.data(cp).mkString(":")) }
    )
  )

  publishMavenStyle := true

  publishArtifact in Test := false

  pomIncludeRepository := { _ => false }

  publishTo <<= version.apply(v => {
    val artifactory = "http://etd-packaging.research.nicta.com.au/artifactory/"
    val flavour = if (v.trim.endsWith("SNAPSHOT")) "libs-snapshot-local" else "libs-release-local"
    val url = artifactory + flavour
    val name = "etd-packaging.research.nicta.com.au"
    Some(Resolver.url(name, new URL(url)))
  })
}
