import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "todolistplayrun"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
    ) 

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}

object Plugins extends Build {
  lazy val plugins = Project("plugins", file("."))
    .dependsOn(
      uri("git://github.com/bseibel/sbt-simple-junit-xml-reporter-plugin.git")
    )
}
