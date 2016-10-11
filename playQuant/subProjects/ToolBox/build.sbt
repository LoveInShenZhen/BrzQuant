name := """ToolBox"""

javacOptions ++= Seq("-parameters", "-Xlint:unchecked", "-Xlint:deprecation", "-encoding", "UTF8")

scalacOptions ++= Seq("-encoding", "UTF8")

kotlinLib("stdlib")
kotlinLib("reflect")

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

scalaSource in Compile := baseDirectory.value / "app"
javaSource in Compile := baseDirectory.value / "app"
resourceDirectory in Compile := baseDirectory.value / "conf"


libraryDependencies += "org.jodd" % "jodd-core" % "3.7"

libraryDependencies += "org.jodd" % "jodd-bean" % "3.7"

libraryDependencies += "org.jodd" % "jodd-http" % "3.7"

libraryDependencies += "org.jodd" % "jodd-mail" % "3.7"

libraryDependencies += "commons-io" % "commons-io" % "2.5"

libraryDependencies += "org.apache.commons" % "commons-exec" % "1.3"

libraryDependencies += "org.simpleframework" % "simple-xml" % "2.7.1"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.39"

libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-kotlin" % "2.7.7"

libraryDependencies += "org.freemarker" % "freemarker" % "2.3.21"