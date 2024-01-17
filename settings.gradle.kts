pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NBA"
include(":app")
include(":core-util")
include(":core-api")
include(":core-compose")
include(":core-domain")
include(":core-navigation")
include(":core-networking")
include(":core-resources")
include(":feature-player-list")
include(":feature-player-detail")
include(":feature-club-detail")
