package com.tools.gradle.utils

import org.gradle.accessors.dm.LibrariesForDeps
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

internal val Project.deps: LibrariesForDeps get() = extensions.getByType()

val LibrariesForDeps.targetSdk: Int get() = versions.sdk.target.get().toInt()
val LibrariesForDeps.minSdk: Int get() = versions.sdk.min.get().toInt()
val LibrariesForDeps.compileSdk: Int get() = versions.sdk.compile.get().toInt()
val LibrariesForDeps.jdkVersion: Int get() = versions.jdk.get().toInt()
