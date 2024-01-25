package com.tools.gradle.detekt

import com.tools.gradle.utils.configureJvmToolchain
import com.tools.gradle.utils.configureKotlinOptions
import com.tools.gradle.utils.deps
import com.tools.gradle.utils.isCiServer
import com.tools.gradle.utils.jdkVersion
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply("io.gitlab.arturbosch.detekt")
            }

            configureJvmToolchain()

            extensions.configure<DetektExtension> {
                config = files("$rootDir/detekt.yml")
                autoCorrect = isCiServer.not()
                parallel = true
                ignoredBuildTypes = listOf("release")
            }

            dependencies {
                "detektPlugins"(deps.detekt.formatting)
            }

            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions.options.configureKotlinOptions(deps.jdkVersion)
            }

            tasks.withType<Detekt>().configureEach {
                jvmTarget = JavaVersion.toVersion(deps.jdkVersion).toString()
            }
        }
    }
}
