name := """json"""

version := "1.0"

scalaVersion := "2.11.7"
val playVersion: String = "2.3.7"
// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

libraryDependencies += "com.typesafe.play" % "play-json_2.11" % "2.4.6"

libraryDependencies ++= Seq(
  "org.pegdown" % "pegdown" % "1.6.0" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.5",
  "com.typesafe.play" %% "play-test" % playVersion,
  "com.typesafe.play" %% "play-ws" % playVersion)