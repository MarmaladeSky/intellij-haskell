lazy val commonSettings = Seq(
  version := "1.0.0-beta88",
  scalaVersion := "2.13.14"
)

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.12" % Test
val sprayJson = "io.spray" %% "spray-json" % "1.3.6"
val snakeYaml = "org.yaml" % "snakeyaml" % "1.30"
val scaffeine = "com.github.blemale" %% "scaffeine" % "5.1.2"
val directories = "io.github.soc" % "directories" % "12"
val fastparse = "com.lihaoyi" %% "fastparse" % "2.3.3"

(ThisBuild / intellijPluginName) := "IntelliJ-Haskell"

lazy val intellijHaskell = (project in file(".")).
  enablePlugins(SbtIdeaPlugin).
  settings(commonSettings: _*).
  settings(
    name := "IntelliJ Haskell",
    javacOptions in Global ++= Seq("-source", "1.8", "-target", "1.8"),
    scalacOptions in Global ++= Seq("-target:jvm-1.8", "-deprecation", "-feature", "-unchecked"),
    libraryDependencies += scalaTest,
    libraryDependencies += sprayJson,
    libraryDependencies += snakeYaml,
    libraryDependencies += scaffeine,
    libraryDependencies += directories,
    libraryDependencies += fastparse,
    (Compile / unmanagedSourceDirectories) += baseDirectory.value / "gen"
  )

(ThisBuild / intellijBuild) := "212.4746.92" // SUCCESS
//(ThisBuild / intellijBuild) := "212.5712.43"
//(ThisBuild / intellijBuild) := "213.5744.223"
//(ThisBuild / intellijBuild) := "213.7172.25"
//(ThisBuild / intellijBuild) := "221.5080.210" SUCCESS
//(ThisBuild / intellijBuild) := "221.6008.13"
//(ThisBuild / intellijBuild) := "222.3345.118" // SUCCESS
//(ThisBuild / intellijBuild) := "222.4554.10"
// java.lang.Throwable: Synchronous execution on EDT: /usr/bin/stack --numeric-version, see com.intellij.execution.process.OSProcessHandler#checkEdtAndReadAction() Javadoc for resolutions

//(ThisBuild / intellijBuild) := "223.7571.182" // SUCCESS, but Java 17
// java.lang.Throwable: Synchronous execution on EDT: /usr/bin/stack --numeric-version, see com.intellij.execution.process.OSProcessHandler#checkEdtAndReadAction() Javadoc for resolutions

//(ThisBuild / intellijBuild) := "223.8836.41" // Success, Java 17, warning about hashcode
//(ThisBuild / intellijBuild) := "231.8109.175" FAILED
//(ThisBuild / intellijBuild) := "231.9423.9"
//(ThisBuild / intellijBuild) := "232.8660.185"
//(ThisBuild / intellijBuild) := "232.10319.17"
//(ThisBuild / intellijBuild) := "233.11799.241"
//(ThisBuild / intellijBuild) := "233.15325.26"
//(ThisBuild / intellijBuild) := "241.14494.240"
//(ThisBuild / intellijBuild) := "241.19072.14"
//(ThisBuild / intellijBuild) := "242.20224.300"
//(ThisBuild / intellijBuild) := "242.20224.387"

intellijPlugins += "com.intellij.java".toPlugin
