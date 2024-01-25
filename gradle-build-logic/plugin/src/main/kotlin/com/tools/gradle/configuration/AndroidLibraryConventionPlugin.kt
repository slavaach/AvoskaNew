package com.tools.gradle.configuration

import com.android.build.gradle.LibraryExtension
import com.tools.gradle.utils.configureJvmToolchain
import com.tools.gradle.utils.configureKotlinAndroid
import com.tools.gradle.utils.proguardRuleFiles
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.tools.gradle.detekt.analysis")
            }

            configureJvmToolchain()

            configureKotlinAndroid()

            extensions.configure<LibraryExtension> {

                defaultConfig.apply {
                    consumerProguardFiles.addAll(proguardRuleFiles())
                }

                buildFeatures {
                    // Отключаем генерацию файла BuildConfig.java
                    buildConfig = false
                }

                buildTypes {
                    getByName("debug") { }
                    getByName("release") { }
                }
            }
        }
    }
}
