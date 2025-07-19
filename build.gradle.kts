// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Ini adalah classpath untuk plugin Gradle Navigation Safe Args itu sendiri
        // Versi harus sesuai dengan versi Navigation Component yang Anda gunakan
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}