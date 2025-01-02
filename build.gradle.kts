// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
}

apply(from = "${rootDir}/scripts/publish-root.gradle")
