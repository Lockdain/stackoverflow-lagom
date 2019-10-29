organization in ThisBuild := "code.personal"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
import com.lightbend.lagom.core.LagomVersion
val akkaServiceDiscovery = "com.lightbend.lagom" %% "lagom-scaladsl-akka-discovery-service-locator" % LagomVersion.current

lazy val `lagom-scala` = (project in file("."))
  .aggregate(`lagom-scala-api`, `lagom-scala-impl`)

lazy val `lagom-scala-api` = (project in file("lagom-scala-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagom-scala-impl` = (project in file("lagom-scala-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      lagomScaladslCluster,
      macwire,
      scalaTest,
      akkaServiceDiscovery
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`lagom-scala-api`)