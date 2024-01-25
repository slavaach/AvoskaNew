package com.tools.gradle.utils

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.configureJvmToolchain() {
    kotlinExtension.jvmToolchain(deps.jdkVersion)
    extensions.configure<JavaPluginExtension> {
        val jdkVersion = JavaVersion.toVersion(deps.jdkVersion)
        sourceCompatibility = jdkVersion
        targetCompatibility = jdkVersion
    }
}