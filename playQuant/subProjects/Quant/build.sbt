name := """Quant"""

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


libraryDependencies += filters

libraryDependencies += "org.jodd" % "jodd-core" % "3.7"

libraryDependencies += "org.jodd" % "jodd-bean" % "3.7"

libraryDependencies += "org.jodd" % "jodd-http" % "3.7"

libraryDependencies += "org.jodd" % "jodd-mail" % "3.7"

libraryDependencies += "org.simpleframework" % "simple-xml" % "2.7.1"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.39"

libraryDependencies += "org.apache.commons" % "commons-csv" % "1.4"

