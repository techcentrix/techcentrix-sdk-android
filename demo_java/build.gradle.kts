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

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("com.techcentrix:android-sdk:1.4.1")
}
