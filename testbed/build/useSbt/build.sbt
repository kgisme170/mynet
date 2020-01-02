import Dependencies._
import sbtassembly.AssemblyPlugin.defaultUniversalScript

val defaultConfigFolder = ".useSbt"
lazy val sparkVersion = "2.11.11"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "useSbt",
      scalaVersion := "2.11.11",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "useSbt",
    aggregate in assembly := false,
    libraryDependencies += scalaTest % Test,
    libraryDependencies += ("com.aliyun.openservices" % "ots-legacy" % "1.1.1-SNAPSHOT")
      .exclude("commons-codec", "commons-codec")
      .exclude("joda-time", "joda-time"),
    libraryDependencies += "com.alibaba.dataos" % "dataos-common" % "0.0.1-SNAPSHOT",
    libraryDependencies += "joda-time" % "joda-time" % "2.9.1"
  )

assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(defaultUniversalScript(shebang = true)))
assemblyJarName in assembly := s"${name.value}"