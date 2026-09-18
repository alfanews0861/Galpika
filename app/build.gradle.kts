import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
}

// ==============================================================================
// Automatic Versioning System for Galpika (గల్పిక)
// Reads base configuration from version.properties and dynamically increments
// on every CI build (GITHUB_RUN_NUMBER) and release generation.
// ==============================================================================
val versionPropsFile = rootProject.file("version.properties")
val versionProps = Properties().apply {
    if (versionPropsFile.exists()) {
        versionPropsFile.inputStream().use { this.load(it) }
    }
}

val baseMajor = (versionProps.getProperty("MAJOR") ?: "1").toInt()
val baseMinor = (versionProps.getProperty("MINOR") ?: "0").toInt()
val basePatch = (versionProps.getProperty("PATCH") ?: "0").toInt()
val baseCode = (versionProps.getProperty("VERSION_CODE") ?: "1").toInt()

val appVersionCode: Int = run {
    val cliOverride = (project.findProperty("versionCode") as? String)?.toIntOrNull()
        ?: System.getenv("VERSION_CODE")?.toIntOrNull()
    if (cliOverride != null) return@run cliOverride

    val ghRunNumber = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull()
    if (ghRunNumber != null) {
        return@run baseCode + ghRunNumber
    }

    baseCode
}

val appVersionName: String = run {
    val cliOverride = (project.findProperty("versionName") as? String)?.takeIf { it.isNotBlank() }
        ?: System.getenv("VERSION_NAME")?.takeIf { it.isNotBlank() }
    if (cliOverride != null) return@run cliOverride

    val patch = basePatch + (appVersionCode - baseCode).coerceAtLeast(0)
    "$baseMajor.$baseMinor.$patch"
}

android {
    namespace = "com.alfanewgen.galpika"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.alfanewgen.galpika"
        minSdk = 26
        targetSdk = 36
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val keystorePath = System.getenv("KEYSTORE_FILE") ?: project.findProperty("KEYSTORE_FILE") as? String
            val storePass = System.getenv("KEYSTORE_PASSWORD") ?: project.findProperty("KEYSTORE_PASSWORD") as? String
            val keyAl = System.getenv("KEY_ALIAS") ?: project.findProperty("KEY_ALIAS") as? String
            val keyPass = System.getenv("KEY_PASSWORD") ?: project.findProperty("KEY_PASSWORD") as? String

            val defaultPass = "galpika12345"
            val defaultAlias = "galpika_release_key"
            val resolvedPass = storePass?.takeIf { it.isNotBlank() } ?: defaultPass
            val resolvedAlias = keyAl?.takeIf { it.isNotBlank() } ?: defaultAlias
            val resolvedKeyPass = keyPass?.takeIf { it.isNotBlank() } ?: defaultPass

            val resolvedStoreFile = when {
                !keystorePath.isNullOrBlank() && file(keystorePath).exists() -> file(keystorePath)
                file("release.keystore").exists() -> file("release.keystore")
                file("../release.keystore").exists() -> file("../release.keystore")
                else -> file(keystorePath ?: "../release.keystore")
            }

            storeFile = resolvedStoreFile
            storePassword = resolvedPass
            keyAlias = resolvedAlias
            keyPassword = resolvedKeyPass
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/*.version"
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/DEPENDENCIES"
        }
    }
    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

dependencies {
    // AndroidX Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.datastore.preferences)

    // Compose BOM & UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Firebase (BOM Managed)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.functions)
    implementation(libs.play.services.auth)

    // Coroutines & Play Services Coroutine extensions
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    // Image loading (Coil)
    implementation(libs.coil.compose)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// ==============================================================================
// Version Management Tasks
// ==============================================================================
tasks.register("printVersion") {
    description = "Displays the current resolved app version code and name"
    doLast {
        println("==========================================")
        println("Galpika App Version Code: $appVersionCode")
        println("Galpika App Version Name: $appVersionName")
        println("==========================================")
    }
}

tasks.register("printVersionCode") {
    description = "Outputs only the version code"
    doLast {
        println(appVersionCode)
    }
}

tasks.register("printVersionName") {
    description = "Outputs only the version name"
    doLast {
        println(appVersionName)
    }
}

tasks.register("bumpVersion") {
    description = "Increments PATCH and VERSION_CODE in version.properties"
    doLast {
        val props = Properties()
        if (versionPropsFile.exists()) {
            versionPropsFile.inputStream().use { props.load(it) }
        }
        val curMajor = (props.getProperty("MAJOR") ?: "1").toInt()
        val curMinor = (props.getProperty("MINOR") ?: "0").toInt()
        val curPatch = (props.getProperty("PATCH") ?: "0").toInt() + 1
        val curCode = (props.getProperty("VERSION_CODE") ?: "1").toInt() + 1

        props.setProperty("MAJOR", curMajor.toString())
        props.setProperty("MINOR", curMinor.toString())
        props.setProperty("PATCH", curPatch.toString())
        props.setProperty("VERSION_CODE", curCode.toString())

        versionPropsFile.outputStream().use {
            props.store(it, "Auto-incremented by Galpika build system")
        }
        println("Galpika: Successfully bumped version to $curMajor.$curMinor.$curPatch (versionCode: $curCode)")
    }
}
