// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin_version}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav_version}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
