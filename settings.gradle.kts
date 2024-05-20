rootProject.name = "kping"
include("core", "server", "auth", "chat", "user", "test")

// Enable the Gradle build cache
gradle.settingsEvaluated {
    startParameter.isBuildCacheEnabled = true
}
