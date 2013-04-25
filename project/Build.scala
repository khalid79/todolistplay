import sbt._
import Keys._
import PlayProject._
import eu.henkelmann.sbt.JUnitXmlTestsListener

object ApplicationBuild extends Build {

    val appName         = "todolistplayrun"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
    )

    testListeners <<= target.map(t => Seq(new eu.henkelmann.sbt.JUnitXmlTestsListener(t.getAbsolutePath)))

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
