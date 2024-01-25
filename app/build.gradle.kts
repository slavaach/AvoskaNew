import com.tools.gradle.utils.signFromPath

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.tools.gradle.android.application")
    //id("com.google.firebase.crashlytics")
    //id("com.google.gms.google-services")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

val versionMajor = 8
val versionMinor = 7
val versionPatch = 0

android {
    namespace = "ru.yandex.slavaach.nullapplicatoin"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.yandex.slavaach.nullapplicatoin"

        versionCode = versionMajor * 1000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }

    signingConfigs {
        getByName("debug") {
            signFromPath(project, "/sign/debug/")
        }
        create("release") {
            signFromPath(project, "/sign/release/")
        }
        create("rustore_debug") {
            signFromPath(project, "/sign/rustore_debug/")
        }
        create("rustore_release") {
            signFromPath(project, "/sign/rustore_release/")
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isDebuggable = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions += "services"
    flavorDimensions += "environment"

    productFlavors {
        create("rustore") {
            dimension = "services"
            versionNameSuffix = "-rustore"
            buildConfigField("String", "MARKET_KEY", "\"rustore\"")
            buildConfigField("String", "REGISTRATION_TYPE_ADDRESS", "\"RUSTORE_ANDROID\"")
            buildConfigField(
                "String",
                "RU_STORE_PUSH_PROJECT_ID",
                "\"ABC\""
            )
            buildTypes {
                getByName("debug") {
                    signingConfig = signingConfigs.getByName("rustore_debug")
                }
                getByName("release") {
                    signingConfig = signingConfigs.getByName("rustore_release")
                }
            }
        }

        create("google") {
            dimension = "services"
            buildConfigField("Boolean", "SAVE_AUTH", "false")
            buildConfigField("String", "MARKET_KEY", "\"google\"")
            buildConfigField("String", "REGISTRATION_TYPE_ADDRESS", "\"FIREBASE_ANDROID\"")
            buildConfigField("String", "RU_STORE_PUSH_PROJECT_ID", "\"\"")
        }


        create("dev") {
            dimension = "environment"
            versionNameSuffix = "-dev"
            buildConfigField("String", "API_DOMAIN", "\"api.openweathermap.org\"")
            buildConfigField("String", "API_URL", "\"https://\" + API_DOMAIN + \"/data/2.5/\"")
            buildConfigField("String", "CRIPTO_SERVICE", "\"true\"")
            buildConfigField("Boolean", "USE_FAKE_SOURCE", "false")
            manifestPlaceholders["mapKey"] = "ABC"
        }
    }
    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    testOptions {
        // Эта настройка заставляет юнит-тесты возвращать значения по умолчанию для
        // методов Android SDK, которые не были замокканы
        unitTests.isReturnDefaultValues = true
        // Эта настройка включает доступ к ресурсам Android во время выполнения юнит-тестов
        unitTests.isIncludeAndroidResources = true
    }
}


dependencies {


    implementation(deps.kotlin.reflect)
    implementation(deps.coroutines)

    implementation(deps.okhttpprofiler)

    implementation(deps.androidx.appcompat)
    implementation(deps.androidx.activity.ktx)
    implementation(deps.androidx.work.ktx)

    /* Other **/
    implementation(deps.material)
    implementation(deps.sdp)
    implementation(deps.timber)
    implementation(deps.jakewharton.threetenabp)
    implementation(deps.bouncycastle)


    /* voyager **/
    implementation(deps.voyager.navigator)
    implementation(deps.voyager.screenmodel)
    implementation(deps.voyager.bottom.sheet.navigator)
    implementation(deps.voyager.tab.navigator)
    implementation(deps.voyager.transitions)
    implementation(deps.voyager.koin)
    implementation(deps.voyager.livedata)

    /* Image **/
    implementation (deps.coil)
    implementation (deps.androidsvg)
    implementation (deps.glide)

    /* Play-services **/
    "googleImplementation" (deps.google.play.core.ktx)
    "googleImplementation" (deps.google.maps.utils)
    "googleImplementation" (deps.google.barcode)
    "googleImplementation" (deps.google.play.services.maps)
    "googleImplementation" (deps.google.play.services.location)

    "rustoreImplementation" (deps.rustore.mapkit)
    "rustoreImplementation" (deps.rustore.appupdate)
    "rustoreImplementation" (deps.rustore.pushclient)
    "rustoreImplementation"(deps.zxing.embedded)
    "rustoreImplementation"(deps.google.zxing)

    /* Koin **/
    implementation(platform(deps.koin.bom))
    implementation(deps.koin.android)
    implementation(deps.koin.compouse)
    testImplementation(deps.koin.android.test)

    /* compose **/
    implementation(deps.android.compose)
    implementation(deps.android.compose.icon)
    implementation(deps.android.compose.viewmodel)
    implementation(deps.android.compose.lifecycle)
    implementation(deps.android.ui.toolin.compose)
    implementation(deps.android.material.compose)
    implementation(deps.android.compose.navigation)
    testImplementation(deps.android.ui.test.compose)
    debugImplementation(deps.android.debug.ui.tooling.compose)
    debugImplementation(deps.android.debug.test.manifest.compose)



    /*ROOM*/
    implementation (deps.androidx.room.runtime)
    implementation (deps.androidx.room.ktx)
    implementation (deps.androidx.room.rxjava2)
    implementation (deps.androidx.room.guava)
    implementation (deps.androidx.room.paging)
    kapt (deps.androidx.room.compiler)
    testImplementation (deps.androidx.room.testing)
    androidTestImplementation (deps.androidx.room.testing)

    /* Network **/
    implementation(deps.okhttp)
    implementation(deps.okhttp.logging)
    implementation(deps.retrofit)
    implementation(deps.retrofit.converter.gson)
    implementation(deps.gson)

    /* DEBUG */
    implementation(deps.uce.handler)

    implementation(deps.spongycastle.core)
    implementation(deps.spongycastle.prov)
    implementation(deps.spongycastle.pkix)
    implementation(deps.spongycastle.pg)

    testImplementation(deps.test.junit)
    testImplementation(deps.test.ext.junit)
    testImplementation(deps.test.runner)
    testImplementation(deps.test.rules)
    testImplementation(deps.test.core.ktx)
    testImplementation(deps.test.robolectric)
    testImplementation(deps.test.mockk)
    testImplementation(deps.test.google.truth)
    testImplementation(deps.test.kotlintest)
    testImplementation(deps.test.mockwebserver)
    testImplementation(deps.test.arch.core.testing)
    testImplementation(deps.coroutines.test)
    testImplementation(deps.test.mockito.kotlin)
    testImplementation(deps.test.mockito.inline)

    testImplementation(deps.test.junit.jupiter.api)
    testRuntimeOnly(deps.test.junit.jupiter.engine)

    androidTestImplementation(deps.test.ext.junit)
    androidTestImplementation(deps.test.runner)
    androidTestImplementation(deps.test.rules)
    androidTestImplementation(deps.test.core.ktx)
    androidTestImplementation(deps.test.mockito.core)
    androidTestImplementation(deps.test.espresso.core)
    androidTestImplementation(deps.test.espresso.contrib)
}
