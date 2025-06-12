pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Prioriza los repositorios de settings.gradle.kts
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GestoLine"
include(":app")

