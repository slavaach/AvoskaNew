package com.tools.gradle.configuration

import com.tools.gradle.utils.configureJvmToolchain
import com.tools.gradle.utils.configureKotlinOptions
import com.tools.gradle.utils.deps
import com.tools.gradle.utils.jdkVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
            }

            configureJvmToolchain()

            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions.options.configureKotlinOptions(deps.jdkVersion)
            }
        }
    }
}
