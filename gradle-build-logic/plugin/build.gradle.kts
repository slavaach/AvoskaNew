plugins {
    `kotlin-dsl`
}

group = "com.tools.gradle.buildlogic"
version = "0.0.1"

dependencies {
    compileOnly(deps.plugin.android)
    compileOnly(deps.plugin.kotlin)
    implementation(deps.gradlePlugin.detekt)
    compileOnly(deps.plugin.ben.manes)
    compileOnly(files(deps.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("gradleAndroidApplication") {
            id = "com.tools.gradle.android.application"
            implementationClass = "com.tools.gradle.configuration.AndroidApplicationConventionPlugin"
        }
        register("GradleAndroidLibrary") {
            id = "com.tools.gradle.android.library"
            implementationClass = "com.tools.gradle.configuration.AndroidLibraryConventionPlugin"
        }
        register("gradleKotlinLibrary") {
            id = "com.tools.gradle.kotlin.library"
            implementationClass = "com.tools.gradle.configuration.KotlinLibraryConventionPlugin"
        }
        register("detekt-analysis") {
            id = "com.tools.gradle.detekt.analysis"
            implementationClass = "com.tools.gradle.detekt.DetektConventionPlugin"
        }
    }
}
