import sbt._
import Keys._
import Tools.onVersion

object build extends Build {
  type Sett = Project.Setting[_]

  val base = Defaults.defaultSettings ++ ScalaSettings.all ++ Seq[Sett](
      organization := "NICTA"
    , version := "1.0-SNAPSHOT"
  )

  val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.2"

  val trackfunction = Project(
    id = "trackfunction"
  , base = file(".")
  , settings = base ++ ReplSettings.all ++ PublishSettings.all ++ InfoSettings.all ++ Seq[Sett](
      name := "trackfunction"
    , libraryDependencies <++= onVersion(
        all = Seq(scalaz)
      )
    )
  )

  val examples = Project(
    id = "examples"
  , base = file("examples")
  , dependencies = Seq(trackfunction)
  , settings = base ++ Seq[Sett](
      name := "trackfunction-examples"
    , fork in run := true
    , libraryDependencies ++= Seq(scalaz)
    , javaOptions in run <++= (fullClasspath in Runtime) map { cp => Seq("-cp", sbt.Build.data(cp).mkString(":")) }
    )
  )

}
