pluginManagement {
    repositories {
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
        jcenter()
        google()
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.library", "com.android.application" -> {
                    useModule("com.android.tools.build:gradle:${requested.version}")
                }
                "kotlinx-serialization" -> {
                    useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
                }
                "io.gitlab.arturbosch.detekt" -> {
                    useModule("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${requested.version}")
                }
            }
        }
    }
}

plugins {
    id( "com.javiersc.resources.autodependencies") version "0.0.11"
}

rootProject.name = "Core"

enableFeaturePreview("GRADLE_METADATA")

include(":core")
include(":docs")
