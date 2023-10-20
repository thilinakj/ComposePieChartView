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
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        // maven { url 'https://jitpack.io' }
    }
}

rootProject.name = "Pie Chart SDK"
include(":pie-chart-view")
include(":piechartsample")
