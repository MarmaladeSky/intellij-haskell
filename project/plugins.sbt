addSbtPlugin("org.jetbrains" % "sbt-idea-plugin" % "3.18.3")

resolvers += Resolver.url("jetbrains-bintray",
  url("https://dl.bintray.com/jetbrains/sbt-plugins/"))(Resolver.ivyStylePatterns)
