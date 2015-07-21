val scalatest = "org.scalatest" %% "scalatest" % "2.2.5" % "test"

val slf4j = "org.slf4j" % "slf4j-api" % "1.7.12"

val logback = "ch.qos.logback" % "logback-classic" % "1.1.3"

lazy val commonSettings = Seq(
  organization := "com.github.morinb",
  name := "scala-readers",
  version := "0.1.0",
  scalaVersion := "2.11.4",
  libraryDependencies ++= Seq(scalatest, slf4j, logback)
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

