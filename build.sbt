name := "eemo2"

version := "2.0-SNAPSHOT"

scalaVersion := "2.11.8"

javaSource in Compile := baseDirectory.value / "src"
javaSource in Test := baseDirectory.value / "test"

unmanagedResourceDirectories in Compile := Seq()
unmanagedResourceDirectories in Test := Seq()
unmanagedSourceDirectories in Compile := Seq(baseDirectory.value / "src")
unmanagedSourceDirectories in Test := Seq(baseDirectory.value / "test")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
)

scalacOptions += "-Xexperimental"

antlr4Settings

antlr4GenListener in Antlr4 := false
antlr4GenVisitor in Antlr4 := true
antlr4PackageName in Antlr4 := Some("me.pennekamp.eemo2.parser")
sourceDirectory in Antlr4 := baseDirectory.value / "antlr"
javaSource in Antlr4 := baseDirectory.value / "gen"
