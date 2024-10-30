plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
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

    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
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

    implementation(libs.recyclerview)
    implementation(libs.lottie)

    // hilt
    api(libs.dagger.hilt.android)
    implementation(libs.core.ktx)
    kapt(libs.dagger.hilt.android.compiler)

    //Glide
    implementation(libs.glide)
    implementation(libs.glide.processor)

    implementation(project(":core:domain"))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Unit Testing
    testImplementation("junit:junit:4.13")
    testImplementation("android.arch.core:core-testing:1.1.1")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.8")
    testImplementation("io.mockk:mockk:1.13.2") 
    testImplementation("org.mockito:mockito-inline:4.0.0")

    // Android Testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.fragment:fragment-testing:1.6.1")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
