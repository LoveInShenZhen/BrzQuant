import KotlinGradleSettings._

name := """playquant"""

lazy val commonSettings = Seq(
  javacOptions ++= Seq("-parameters", "-Xlint:unchecked", "-Xlint:deprecation", "-encoding", "UTF8"),
  scalacOptions ++= Seq("-encoding", "UTF8"),
  publishMavenStyle := true,
  organization := "love.in.shenzhen",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  sources in(Compile, doc) := Seq.empty,
  libraryDependencies ++= Seq(
    javaJdbc,
    cache,
    javaWs,
    filters,
    "org.jetbrains.kotlin" % "kotlin-runtime" % "1.0.4",
    "org.jetbrains.kotlin" % "kotlin-stdlib" % "1.0.4",
    "org.jetbrains.kotlin" % "kotlin-reflect" % "1.0.4",
    "org.jodd" % "jodd-core" % "3.7",
    "org.jodd" % "jodd-bean" % "3.7",
    "org.jodd" % "jodd-http" % "3.7",
    "org.jodd" % "jodd-mail" % "3.7",
    "mysql" % "mysql-connector-java" % "5.1.39",
    "commons-io" % "commons-io" % "2.5",
    "org.apache.commons" % "commons-csv" % "1.4",
    "org.apache.commons" % "commons-exec" % "1.3",
    "org.simpleframework" % "simple-xml" % "2.7.1",
    "org.freemarker" % "freemarker" % "2.3.21",
    "com.fasterxml.jackson.module" % "jackson-module-kotlin" % "2.7.7"
  )
)

lazy val sharedTools = (project in file("subProjects/ToolBox"))
  .settings(commonSettings: _*)
  .enablePlugins(PlayJava, PlayEbean)

lazy val apiBox = (project in file("subProjects/Quant"))
  .settings(commonSettings: _*)
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(sharedTools)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(sharedTools)
  .dependsOn(apiBox)

kotlinCompile := {
  val currentPath = file(".").absolutePath
  KotlinGradleComplie.BuildByGradle(streams.value)
}

compile in Compile <<= (compile in Compile) dependsOn kotlinCompile

kotlinClean := {
  KotlinGradleComplie.CleanByGradle(streams.value)
}

clean <<= clean dependsOn kotlinClean

publishArtifact in(Compile, packageDoc) := false

