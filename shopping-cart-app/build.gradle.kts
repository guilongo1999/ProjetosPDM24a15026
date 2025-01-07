//import org.jetbrains.kotlin.gradle.internal.kapt.incremental.UnknownSnapshot.classpath

// Top-level build file where you can add configuration options common to all sub-projects/modules.


/*

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false

}




 */



buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("com.google.gms:google-services:4.4.2")
    }
}

plugins {
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false

}
