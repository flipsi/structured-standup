inThisBuild(
  List(
    organization := "com.sflip",
    version := "0.1-SNAPSHOT",
    scalaVersion := DependencyVersions.scala,
    scalaVersion := "2.13.4",
    crossScalaVersions := Seq("2.12.11", scalaVersion.value)
  )
)

lazy val frontend =
  crossProject(JSPlatform)
    .crossType(CrossType.Pure)
    .in(file("modules/frontend"))
    .jsSettings(
      name := "frontend",
      scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
      scalaJSLinkerConfig ~= {
        _.withESFeatures(_.withUseECMAScript2015(false))
      },
      scalaJSUseMainModuleInitializer := true,
      libraryDependencies ++= Seq.concat(
        Dependencies.scribe.value,
        Dependencies.laminar.value,
        Dependencies.frontroute.value
      ),
      ScalaOptions.fixOptions
    )

lazy val root = project
  .in(file("."))
  .settings(
    name := "Structured Standup",
    normalizedName := "structured-standup"
  )
  .aggregate(frontend.js)
