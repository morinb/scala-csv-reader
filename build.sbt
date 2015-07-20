val scalatest = "org.scalatest" %% "scalatest" % "2.2.5" % "test"

lazy val commonSettings = Seq(
  organization := "com.github.morinb",
  name := "scala-readers",
  version := "0.1.0",
  scalaVersion := "2.11.4",
  libraryDependencies += scalatest
)


lazy val csv = (project.dependsOn(util) in file("csv")).
  settings(commonSettings: _*).
  settings(
    name := "scala-csv-reader"
  )


lazy val util = (project in file("util")).
  settings(commonSettings: _*).
  settings(
    name := "scala-readers-util"
  )

lazy val root = (project in file(".")).
  aggregate(csv, util)

