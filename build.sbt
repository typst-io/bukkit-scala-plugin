import sbt.Def.spaceDelimited

import java.nio.file.{Files, StandardCopyOption}

ThisBuild / organization := "io.typecraft"

val circeVersion = "0.14.1"

lazy val copyJar = InputKey[Unit]("copyJar")

lazy val plugin = (project in file("plugin"))
  .settings(
    name := "bukkit-scala-plugin-legacy",
    version := "1.15.0",
    scalaVersion := "2.13.10",
    crossScalaVersions := Seq("2.13.11", "3.2.1"),
    resolvers ++= Seq(
      Resolver.mavenLocal
    ),
    libraryDependencies ++= Seq(
      "org.spigotmc" % "spigot-api" % "1.12.2-R0.1-SNAPSHOT" % "provided",
      "io.typecraft" % "bukkit-view-core" % "5.1.3",
      "io.typecraft" % "command-bukkit" % "1.0.0",
      "io.typecraft" % "command-scala" % "1.0.0",
      "io.typecraft" % "bukkit-object" % "0.4.1",
      "io.typecraft" %% "ender-core" % "0.1.0-SNAPSHOT", // TODO: why this not contains in `ender-bukkit` as a transitive
      "io.typecraft" %% "ender-bukkit" % "0.1.0-SNAPSHOT",
      "org.typelevel" %% "cats-core" % "2.7.0",
      "org.tpolecat" %% "doobie-core" % "1.0.0-RC2",
      "io.circe" %% "circe-yaml-legacy" % "0.14.2-50-8e704e6"
    ) ++ Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
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
