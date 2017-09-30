name := "Elastic4s"

version := "1.0"

scalaVersion := "2.12.3"

val elastic4sVersion = "0.90.2.8"

libraryDependencies ++= Seq(
   "com.sksamuel.elastic4s" %% "elastic4s-core" % "5.5.3",
   "com.sksamuel.elastic4s" %% "elastic4s-tcp" % "5.5.3",
   "com.sksamuel.elastic4s" %% "elastic4s-http" % "5.5.3",
   "com.sksamuel.elastic4s" %% "elastic4s-streams" % "5.5.3",
   "com.sksamuel.elastic4s" %% "elastic4s-circe" % "5.5.3",
   "org.apache.logging.log4j" % "log4j-api" % "2.9.1",
   "org.apache.logging.log4j" % "log4j-core" % "2.9.1",
   "com.sksamuel.elastic4s" %% "elastic4s-testkit" % "5.5.3" % "test"
)