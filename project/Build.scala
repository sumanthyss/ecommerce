import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "ShoppingCart"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "mysql" % "mysql-connector-java" % "5.1.18",
      "org.webjars" % "webjars-play" % "2.0",
      "org.webjars" % "bootstrap" % "2.2.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here
    )

}
