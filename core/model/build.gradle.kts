plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
}

dependencies {
    // moshi
    implementation(libs.moshi)
    implementation(libs.moshi.ktx)
    implementation(libs.moshi.adapter)
    implementation(libs.moshi.codegen)
}