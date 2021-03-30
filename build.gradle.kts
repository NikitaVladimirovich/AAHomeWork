// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin_version}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav_version}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.0.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

repositories {
    // Required to download KtLint
    mavenCentral()
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
