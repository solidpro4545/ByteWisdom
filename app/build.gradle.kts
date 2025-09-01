plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.kapt")
}




android {
    namespace = "com.example.bytewisdom"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bytewisdom"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    // Java 17
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures { compose = true }

    // Compose compiler compatible with Kotlin 2.0.21
    composeOptions { kotlinCompilerExtensionVersion = "1.5.15" }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Compose BOM from catalog
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // ----- Room (via kapt) -----
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // ----- Jetpack / Compose -----
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)

    // Lifecycle (you can move these to the catalog later if you want)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Retrofit + Moshi
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}
