name := "exsure"

version := "1.0"

scalaVersion := "2.11.8"

organization := "exsure"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  //  "-Xlint",
  "-Yinline-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  //  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import"
  //  "-Ylog-classpath"
)

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked", "-Xlink:-warn-missing-interpolator", "-g:vars")

cancelable in Global := true

def javaVersion(version: String): Seq[Def.Setting[Task[Seq[String]]]] = Seq(
  javacOptions ++= Seq("-source", version, "-target", version),
  scalacOptions += s"-target:jvm-$version"
)

lazy val Benchmark = config("bench") extend Test

def commonSettings = Seq(
  organization := "exsure",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "com.iheart" %% "ficus" % "1.1.3",
    "org.scalatest" %% "scalatest" % "3.0.0-M16-SNAP6" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.1" % "test",
    "com.storm-enroute" %% "scalameter" % "0.7" % "test",
    "org.typelevel" %% "discipline" % "0.5" % "test"
  ),
  testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
  parallelExecution in Benchmark := false,
  parallelExecution in Test := false
)

lazy val root = project.in(file("."))
  .aggregate(server)

lazy val server = project.in(file("server"))
  .settings(moduleName := "server")
  .settings(javaVersion("1.8"))
  .settings(commonSettings)
  .settings{
    libraryDependencies ++= Seq(
    )
  }
  .configs(Benchmark)
  .settings(inConfig(Benchmark)(Defaults.testSettings): _*)
