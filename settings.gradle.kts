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
        // Optional fallback (use only if you're in a region with mirror issues)
        // maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "StudyBuddy"
include(":app")
