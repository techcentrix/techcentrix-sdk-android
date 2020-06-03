plugins {
    id("com.android.application")
}

val mobileApiKey: String by project
val oneTimeToken: String by project

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"

    defaultConfig {
        applicationId = "com.techcentrix.sdk.demo_java"

        minSdkVersion(21)
        targetSdkVersion(29)

        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named("debug") {
            buildConfigField("String", "ONE_TIME_TOKEN", "\"${oneTimeToken}\"")
        }

        named("release") {
            buildConfigField("String", "ONE_TIME_TOKEN", "\"${oneTimeToken}\"")

            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

val appCompatVersion: String by rootProject.extra
val materialVersion: String by rootProject.extra
val androidxCoreVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val androidxPreferenceVersion: String by rootProject.extra
val androidxLifecycleVersion: String by rootProject.extra
val firebaseMessagingVersion: String by rootProject.extra

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // TechCentrix SDK
    implementation("com.techcentrix:android-sdk:1.4.3")

    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.preference:preference-ktx:$androidxPreferenceVersion")
    implementation("com.google.android.material:material:$materialVersion")

    implementation("androidx.lifecycle:lifecycle-livedata:$androidxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel:$androidxLifecycleVersion")

    implementation("com.google.firebase:firebase-messaging:$firebaseMessagingVersion")
}
