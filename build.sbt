import Dependency._

lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .settings(
    name := "gatling_test",
    version := "0.1",
    scalaVersion := "2.12.4",
    libraryDependencies ++= gatling
)