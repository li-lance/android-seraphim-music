plugins {
    alias(libs.plugins.seraphim.android.application)
    alias(libs.plugins.seraphim.android.application.compose)
    alias(libs.plugins.seraphim.android.application.jacoco)
//    alias(libs.plugins.seraphim.android.application.firebase)
    alias(libs.plugins.seraphim.koin)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.seraphim.music"
    defaultConfig {
        applicationId = "com.seraphim.music"
        versionCode = 8
        versionName = "0.1.2" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        multiDexEnabled = true

    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    kotlinOptions {
        freeCompilerArgs += "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
}
dependencies {
    implementation(project(":core:permissions"))
    implementation(project(":core:auth"))
    implementation(project(":shared"))
    implementation(project(":domain:login"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.koin.androidx.compose)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.auth)
    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.logback.android)
    implementation(libs.slf4j.api)
    implementation(libs.destinations.core)
    implementation(libs.androidx.palette.ktx)
    ksp(libs.destinations.ksp)
    implementation(libs.destinations.bottom.sheet)
    implementation(libs.process.phoenix)
    implementation(libs.napier.loggger)
    implementation(libs.mmkv.kotlin)
}
baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false

    // Make use of Dex Layout Optimizations via Startup Profiles
    dexLayoutOptimization = true
}
