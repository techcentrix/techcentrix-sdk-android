import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.github.ben-manes.versions") version "0.28.0"
}

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath(kotlin("gradle-plugin", version = "1.3.72"))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

val androidxCoreVersion by extra("1.2.0")
val appCompatVersion by extra("1.1.0")
val materialVersion by extra("1.1.0")
val androidxPreferenceVersion by extra("1.1.1")
val androidxLifecycleVersion by extra("2.2.0")
val constraintLayoutVersion by extra("1.1.3")
val coroutinesVersion by extra("1.3.7")
val firebaseMessagingVersion by extra("20.2.0")

// https://github.com/ben-manes/gradle-versions-plugin/blob/master/examples/kotlin/build.gradle.kts
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

val repoUsername: String by project
val repoPassword: String by project

allprojects {
    repositories {
        google()
        jcenter()

        maven("https://maven.techcentrix.com/artifactory/android-sdk-release") {
            credentials {
                username = repoUsername
                password = repoPassword
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
