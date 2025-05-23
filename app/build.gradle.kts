plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.devtools.ksp") version "2.1.10-1.0.29"
}

android {
    namespace = "com.example.mobileapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mobileapp"
        minSdk = 33
        //noinspection OldTargetApi
        targetSdk = 34
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.camera.extensions)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.firebase.database)
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.firebase.analytics.ktx)
    implementation(libs.java.websocket)
    implementation(libs.androidx.webkit)
    implementation(libs.androidx.storage)
    // okhttp
    implementation(libs.okhttp)
    // Work manager
    implementation(libs.androidx.work.runtime.ktx)
    // Datastore
    implementation(libs.androidx.datastore.preferences)
    // Database
    implementation(libs.firebase.core)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.media3.common.ktx)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}