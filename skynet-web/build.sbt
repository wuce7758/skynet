name := """skynet-web"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.10.1"
libraryDependencies += "org.apache.httpcomponents" % "fluent-hc" % "4.3.3"
libraryDependencies += "org.apache.commons" % "commons-email" % "1.4"


