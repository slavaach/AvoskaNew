package com.tools.gradle.utils

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid() = with(androidExtension){
        compileSdk = deps.compileSdk

        defaultConfig {
            minSdk = deps.minSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            val jdkVersion = JavaVersion.toVersion(deps.jdkVersion)
            sourceCompatibility = jdkVersion
            targetCompatibility = jdkVersion
        }

        kotlinOptions {
            configureKotlinOptions(deps.jdkVersion)
            allWarningsAsErrors.set(findProperty("warningsAsErrors") == "true")
        }
}

internal fun AndroidExtension.kotlinOptions(block: KotlinJvmCompilerOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure<KotlinJvmOptions> { block(options) }
}

internal fun KotlinJvmCompilerOptions.configureKotlinOptions(jdkVersion: Int) {
    jvmTarget.set(JvmTarget.fromTarget(jdkVersion.toString()))
    freeCompilerArgs.add("-Xinline-classes")
    freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
}