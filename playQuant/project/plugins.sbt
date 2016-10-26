// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.9")

// addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "3.0.2")

libraryDependencies += "org.jodd" % "jodd-core" % "3.7"
