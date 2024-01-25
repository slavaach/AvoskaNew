package com.tools.gradle.configuration

import com.android.build.api.dsl.ApplicationExtension
import com.tools.gradle.utils.configureJvmToolchain
import com.tools.gradle.utils.configureKotlinAndroid
import com.tools.gradle.utils.deps
import com.tools.gradle.utils.targetSdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.tools.gradle.detekt.analysis")
            }

            configureJvmToolchain()

            configureKotlinAndroid()

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = deps.targetSdk
            }
        }
    }
}
