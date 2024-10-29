plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.android.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
        }

        create("prod") {
            dimension = "environment"
        }
    }

    buildFeatures.buildConfig = true

}

dependencies {
    implementation(libs.kotlin.coroutine)

    // hilt
    api(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)

    api(project(":core:data"))
}


// Allow references to generated code
kapt {
    correctErrorTypes = true
}