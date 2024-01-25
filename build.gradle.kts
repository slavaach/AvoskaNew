// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath(deps.plugin.android)
        classpath(deps.plugin.kotlin)
        //classpath(deps.plugin.google.services)
        //classpath(deps.plugin.firebase.crashlytics)
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.22.0")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://artifactory-external.vkpartner.ru/artifactory/maven")

    }
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}
