import sbt._
import Keys._

object JacksBuild extends Build {
  val buildSettings = Project.defaultSettings ++ Seq(
    name         := "jacks",
    version      := "2.2.4", // really 2.2.3, but we published an updated 2.10/2.11 release
    organization := "com.cunei",
    scalaVersion := "2.10.5",

    crossScalaVersions := Seq("2.12.1", "2.11.6", "2.10.5"),

    libraryDependencies <+= scalaVersion("org.scala-lang" % "scalap" % _),
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.3",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
    ),

    scalacOptions ++= Seq("-unchecked", "-optimize"),
    scalacOptions <++= scalaVersion map {
      CrossVersion.partialVersion(_) match {
        case Some((m, n)) if m > 2 || n >= 10 => Seq("-language:_")
        case _                                => Nil
      }
    },

    publishArtifact in Test := false,
    publishMavenStyle := false,
    publishTo := Some(Resolver.url("typesafe-dbuild-temp", new URL("http://typesafe.artifactoryonline.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)),
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials-typesafe-dbuild"),

    pomIncludeRepository := { _ => false },
    pomExtra             := (
      <url>http://github.com/wg/jacks</url>

      <scm>
        <connection>scm:git:git://github.com/cunei/jacks.git</connection>
        <developerConnection>scm:git:git://github.com/cunei/jacks.git</developerConnection>
        <url>http://github.com/cunei/jacks</url>
      </scm>

      <licenses>
        <license>
          <name>Apache License, Version 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          <distribution>repo</distribution>
        </license>
      </licenses>

      <developers>
        <developer>
          <id>will-cunei</id>
          <name>Will Glozer (some patches: Antonio Cunei)</name>
        </developer>
      </developers>
    )
  )

  val jacks = Project(id = "jacks", base = file("."), settings = buildSettings)
}
