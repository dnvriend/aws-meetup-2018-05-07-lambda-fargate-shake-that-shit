lazy val `ffmpeg-thumb-lambda` = (project in file("."))
  .settings(
    libraryDependencies += "com.github.dnvriend" %% "sam-annotations" % "1.0.30",
    libraryDependencies += "com.github.dnvriend" %% "sam-lambda" % "1.0.30",
    resolvers += Resolver.bintrayRepo("dnvriend", "maven"),
    scalaVersion := "2.12.6",
    samStage := "dev",
    organization := "io.binx",
    description := "simple sam component with endpoints",
    name := "ffmpeg-thumb-lambda"
  )


