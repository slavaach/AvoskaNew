pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        addAll(pluginManagement.repositories.toList())
    }

    versionCatalogs {
        create("deps") {
            from(files("../gradle/deps.versions.toml"))
        }
    }
}

rootProject.name = "gradle-build-logic"
include(":plugin")
