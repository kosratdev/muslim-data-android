pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        jcenter()
        google()
        maven(url = "https://jitpack.io")
        mavenCentral()
    }
}

rootProject.name = "Muslim Data Example"
include(":app", ":muslim-data")