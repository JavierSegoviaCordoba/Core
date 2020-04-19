plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
    id("io.gitlab.arturbosch.detekt")
    MavenPublish
    Dokka
}

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
    jcenter()
    google()
}

group = "com.javiersc.resources"
version = "0.0.1"

android.defaultConfig()

kotlin {
    jvm()
    android {
        publishLibraryVariants("release", "debug")
    }
    sourceSets {
        commonMain {
            dependencies { commonMainDependencies.forEach(::implementation) }
        }
        commonTest {
            dependencies { commonTestDependencies.forEach(::implementation) }
        }
        val jvmMain by getting {
            dependencies { jvmMainDependencies.forEach(::implementation) }
        }
        val jvmTest by getting {
            dependencies { jvmTestDependencies.forEach(::implementation) }
        }
        val androidMain by getting {
            dependsOn(jvmMain)
            dependencies { androidMainDependencies.forEach(::implementation) }
        }
        val androidTest by getting {
            dependsOn(jvmTest)
        }
    }
}
