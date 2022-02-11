ThisBuild / version := "1.0.0"
ThisBuild / organization := "io.typecraft"

ThisBuild / scalaVersion := "3.1.1"

lazy val plugin = (project in file("plugin"))
  .settings(
    name := "bukkit-scala-plugin",
    resolvers ++= Seq(
      Resolver.mavenLocal
    ),
    libraryDependencies ++= Seq(
      "org.spigotmc" % "spigot-api" % "1.18.1-R0.1-SNAPSHOT" % "provided"
    )
  )
