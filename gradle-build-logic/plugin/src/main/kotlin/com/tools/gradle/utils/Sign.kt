package com.tools.gradle.utils

import com.android.build.api.dsl.SigningConfig
import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.util.Properties

fun SigningConfig.signFromPath(project: Project, path: String) {
    val signPath = File("${project.projectDir}/$path").canonicalPath
    val filePath = File(signPath)
    if (!filePath.exists())
        throw IllegalArgumentException("Bad sign path $signPath")

    val prop = openProperties(File("${filePath.absolutePath}/data.properties"))
    storeFile = File("${filePath.absolutePath}/keystore.jks")
    storePassword = prop.getProperty("keystorePassword")
    keyAlias = prop.getProperty("keyAlias")
    keyPassword = prop.getProperty("keyPassword")
}

private fun openProperties(file: File): Properties {
    val properties = Properties()
    if (file.exists())
        properties.load(FileInputStream(file))
    return properties
}