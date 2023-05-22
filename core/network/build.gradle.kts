plugins {
    id("kotlin") apply true
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlin)
    implementation(libs.bundles.network)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.converter)
}