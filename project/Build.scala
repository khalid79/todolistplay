import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "todolistplayrun"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      // Add your project dependencies here,
    ) 

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
/*    	publishTo := Some(
    		"My resolver" at "http://ec2-54-236-226-98.compute-1.amazonaws.com/nexus/content/repositories/releases"
    	),

    	credentials += Credentials(
    		"Releases", "http://ec2-54-236-226-98.compute-1.amazonaws.com/nexus/", "deployment", "deploy"
    	)    */
    )

}
