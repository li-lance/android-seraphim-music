plugins {
    alias(libs.plugins.seraphim.jvm.library)
    alias(libs.plugins.kotlin.serialization)
}
dependencies{
    implementation(libs.kotlinx.serialization.json)
}