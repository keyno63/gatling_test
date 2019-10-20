import sbt._

object Dependency {
  val pluginVersion: Map[String, String] = Map(
    "gatling" -> "3.0.0"/*"2.2.2"*/
  )
  val buildVersion: Map[String, String] = Map(
    "gatling" -> "3.2.1"/*"2.2.2"*/
  )

  val gatling: Seq[ModuleID] = Seq(
    "io.gatling" % "gatling-test-framework",
    "io.gatling.highcharts" % "gatling-charts-highcharts"
  ).map(_ % buildVersion("gatling") % "test")
}
