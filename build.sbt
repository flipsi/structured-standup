enablePlugins(ScalaJSPlugin)

enablePlugins(ScalaJSBundlerPlugin) // only needed for your JS dependencies, not for Laminar

name := "Structured Standup"

version := "0.1-SNAPSHOT"

normalizedName := "structured-standup"

organization := "com.sflip"

scalaVersion := "2.13.4"

crossScalaVersions := Seq("2.12.11", "2.13.4")

libraryDependencies ++= Seq(
  "com.raquo" %%% "laminar" % "0.12.2"
)

scalaJSUseMainModuleInitializer := true

scalaJSLinkerConfig in (Compile, fastOptJS) ~= { _.withSourceMap(false) }
scalaJSLinkerConfig in (Compile, fullOptJS) ~= { _.withSourceMap(false) }

useYarn := true
