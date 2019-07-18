organization := "info.voidstar.tool.cli"

name := "climt"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  "org.rogach" %% "scallop" % "3.3.1",
)

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
)