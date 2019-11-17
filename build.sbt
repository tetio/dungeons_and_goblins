name := "dungeons_and_goblins"

version := "0.1"

scalaVersion := "2.12.7"


libraryDependencies ++= List(
  "com.googlecode.lanterna" % "lanterna" % "3.0.1",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.pegdown"    %  "pegdown"     % "1.6.0"  % "test"
)
