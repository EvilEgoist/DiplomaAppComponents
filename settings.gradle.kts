rootProject.name = "DiplomaAppComponents"
include(":app")
include(":core:ui")
include(":core:network")
include(":core:base")
include(":features:imageGallery")
include(":features:anotherTestFeature")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
