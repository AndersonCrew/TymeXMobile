plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.android.tymexmobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.tymexmobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "environment"

    signingConfigs {
        create("prod") {
            storeFile = file("../config/keystore.jks")
            storePassword = "TymeX123!@#"
            keyAlias = "TymeXProduct"
            keyPassword = "TymeX123!@#"
        }

        create("dev") {
            storeFile = file("../config/keystore.jks")
            storePassword = "TymeX123!@#"
            keyAlias = "TymeXDev"
            keyPassword = "TymeX123!@#"
        }
    }

    productFlavors {
        create("dev") {
            dimension = "environment"
            versionCode = 59
            versionName = "1.0.0"
            versionNameSuffix = "-dev"
            signingConfig = signingConfigs["dev"]
            resValue("string", "app_name", "TymeX-Dev")
            buildConfigField("boolean", "ENABLE_CRASHLYTICS_COLLECT", "true")
        }

        create("prod") {
            dimension = "environment"
            versionCode = 19
            versionName = "1.0.0"
            versionNameSuffix = ""
            signingConfig = signingConfigs["prod"]
            resValue("string", "app_name", "TymeX")
            buildConfigField("boolean", "ENABLE_CRASHLYTICS_COLLECT", "true")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // navigation
    implementation(libs.navigation.runtime)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // hilt
    api(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)


    implementation(project(":core:data"))


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
