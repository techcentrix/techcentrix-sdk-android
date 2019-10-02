// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.5.1")
        classpath(kotlin("gradle-plugin", version = "1.3.50"))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
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
