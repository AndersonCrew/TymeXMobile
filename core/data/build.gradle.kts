plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
}

dependencies {
    api(project(":core:model"))

    // retrofit
    api(libs.retrofit.core)
    api(libs.retrofit.gson)
    api(libs.retrofit.moshi)
    api(libs.okhttp)
    api(libs.okhttp.logging)
}