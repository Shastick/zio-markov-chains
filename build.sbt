// *****************************************************************************
// Projects
// *****************************************************************************

lazy val root =
  project
    .in(file("."))
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
        library.zio % Provided,
        library.zioStreams % Provided,
        library.zioLogging % Provided,
        library.zioMetrics % Provided,
        // library.metricsCore % Provided,
        library.zioTest    % Test,
        library.zioTestSbt % Test
      ),
      publishArtifact := true,
      testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {

    object Version {
      val zio = "2.0.0-RC6"
    }

    val zio        = "dev.zio" %% "zio"          % Version.zio
    val zioTest    = "dev.zio" %% "zio-test"     % Version.zio
    val zioTestSbt = "dev.zio" %% "zio-test-sbt" % Version.zio
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
    scalafmtSettings ++
    commandAliases

lazy val commonSettings =
  Seq(
    name := "zio-prefetcher",
    scalaVersion := "3.1.2",
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true
  )

lazy val commandAliases =
  addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt") ++
    addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

// Fix that annoying "scalac: 'nullary-override' is not a valid choice for '-Xlint'" error
scalacOptions ~= { opts => opts.filterNot(Set("-Xlint:nullary-override")) }

