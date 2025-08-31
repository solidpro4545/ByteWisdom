plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.compose) // compose plugin (from version catalog)
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

    // ➜ Align Java compile to 17
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures { compose = true }

    // optional with Kotlin 2.0 + compose plugin
    composeOptions { kotlinCompilerExtensionVersion = "1.5.15" }
}

// ➜ Use Kotlin JVM toolchain 17
kotlin {
    jvmToolchain(17)
}

// (Optional but harmless) ensure all Kotlin tasks target 17
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.08.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // LiveData -> Compose
    implementation("androidx.compose.runtime:runtime-livedata")
}
