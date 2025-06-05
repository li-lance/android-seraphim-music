plugins {
    alias(libs.plugins.seraphim.android.library)
}
android {
    namespace = "com.seraphim.domain.login"
    buildFeatures {
        viewBinding = true
    }
}
dependencies {
    implementation(project(":core:auth"))
    implementation(project(":shared"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.openid.auth)
    implementation(libs.koin.android)
}