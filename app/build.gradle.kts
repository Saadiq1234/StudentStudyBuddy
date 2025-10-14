plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    // Hilt plugin
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.compose.compiler)



}



android {
    namespace = "com.studybuddy.app" // Use '=' instead of a space
    compileSdk = 34                  // Use '=' instead of a space

    defaultConfig {
        applicationId = "com.studybuddy.app" // Use '='
        minSdk = 21                         // Use '='
        targetSdk = 34                      // Use '='
        versionCode = 1                     // Use '='
        versionName = "1.0"                 // Use '='

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Use '='
        vectorDrawables {
            useSupportLibrary = true        // Use '='
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Use '=' and the property name "isMinifyEnabled"
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Use '='
        targetCompatibility = JavaVersion.VERSION_11 // Use '='
    }


    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true // Use an equals sign for assignment
    }




    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core & Compose
    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation ("androidx.compose.ui:ui:1.4.3")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation ("androidx.compose.material3:material3:1.1.0")
    implementation ("androidx.navigation:navigation-compose:2.6.0")

    // Lifecycle / ViewModel
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Room (Database)
    implementation ("androidx.room:room-runtime:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")

    // Retrofit (Networking)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Firebase (BOM for version management)

    implementation (platform("com.google.firebase:firebase-bom:34.3.0")) // Or a newer version
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-messaging-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx") // Added for file storage
    implementation("com.google.firebase:firebase-analytics")
    // Hilt (Dependency Injection)
    implementation ("com.google.dagger:hilt-android:2.51")
    kapt ("com.google.dagger:hilt-android-compiler:2.51")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Coil (Image Loading)
    implementation ("io.coil-kt:coil-compose:2.3.0")

    // WorkManager (Background Tasks)
    implementation ("androidx.work:work-runtime-ktx:2.8.1")

    // Testing

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.3")


}










