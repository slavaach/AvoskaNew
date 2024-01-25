package com.tools.gradle.utils

import org.gradle.api.Project
import java.io.File

val isCiServer = System.getenv("CI") == "true"

fun Project.proguardRuleFiles(): List<File> {
    return fileTree("proguard") { include("*.pro") }.toList()
}
