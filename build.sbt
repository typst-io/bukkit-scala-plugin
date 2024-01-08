import sbt.Def.spaceDelimited

import java.nio.file.{Files, StandardCopyOption}

ThisBuild / organization := "io.typecraft"

val circeVersion = "0.14.1"

lazy val copyJar = InputKey[Unit]("copyJar")

lazy val plugin = (project in file("plugin"))
  .settings(
    name := "bukkit-scala-plugin",
    version := "2.0.0",
    scalaVersion := "2.13.11",
    crossScalaVersions := Seq("2.13.11", "3.1.1"),
    resolvers ++= Seq(
      Resolver.mavenLocal
    ),
    libraryDependencies ++= Seq(
      "org.spigotmc" % "spigot-api" % "1.18.1-R0.1-SNAPSHOT" % "provided",
      "io.typecraft" % "bukkit-view-core" % "6.0.2",
      "io.typecraft" % "command-bukkit" % "1.1.1",
      "io.typecraft" % "command-scala" % "1.1.1",
      "io.typecraft" % "bukkit-object" % "0.4.1",
      "io.typecraft" %% "ender-core" % "0.1.0-SNAPSHOT", // TODO: why not be contained `ender-bukkit` as a transitive
      "io.typecraft" %% "ender-bukkit" % "0.1.0-SNAPSHOT",
      "org.typelevel" %% "cats-core" % "2.7.0",
      "org.tpolecat" %% "doobie-core" % "1.0.0-RC2"
    ) ++ Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser",
      "io.circe" %% "circe-yaml"
    ).map(_ % circeVersion),
    assemblyMergeStrategy := {
      case PathList("plugin.yml") => MergeStrategy.first
      case x =>
        val oldStrategy = assemblyMergeStrategy.value
        oldStrategy(x)
    },
    copyJar := {
      val path: String = spaceDelimited("<arg>").parsed.head
      val sourceJarFile: File = (assembly / assemblyOutputPath).value
      val jarName = (assembly / assemblyJarName).value
      val dest = new File(path, jarName)
      dest.getParentFile.mkdirs()
      Files.copy(sourceJarFile.toPath, new File(path, jarName).toPath, StandardCopyOption.REPLACE_EXISTING)
    },
    publishM2Configuration := publishM2Configuration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(
      true
    )
  )
