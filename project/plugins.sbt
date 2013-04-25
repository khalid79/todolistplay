// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Scala Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

addSbtPlugin("eu.henkelmann" % "junit_xml_listener" % "0.4-SNAPSHOT")

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.1")