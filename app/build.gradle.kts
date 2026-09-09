plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.donutapptest"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.donutapptest"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    // room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.ktx)

    // retrofit
    implementation(libs.retrofit)
    // Retrofit with Scalar Converter
    implementation(libs.converter.scalars)
    // Retrofit with Gson Converter
    implementation(libs.converter.gson)

    //compose navigation
    implementation(libs.androidx.navigation.compose)

    //material icons extended
    implementation(libs.androidx.material.icons.extended)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v240)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // datastore
    implementation(libs.androidx.datastore.preferences)

    // Material 3
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.window.size.class1)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil for image loading
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Lottie for animations
    implementation(libs.lottie.compose)
}