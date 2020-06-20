plugins {
    KotlinMultiplatform
//    JaCoCo
    Detekt
    MavenPublish
    Nexus
    Dokka
}

repositories {
    google()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    mavenCentral()
    jcenter()
}

val coreVersion: String by project
val isCoreReleaseEnv: Boolean? = System.getenv("isCoreReleaseEnv")?.toBoolean()
val isCoreRelease: String by project

val finalVersion = coreVersion.generateVersion(isCoreReleaseEnv ?: isCoreRelease.toBoolean())

group = "com.javiersc.resources"
version = finalVersion

val dokkaJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
    dependsOn(tasks.dokka)
}

android.defaultConfig()

kotlin {
    jvm {
        mavenPublication {
            artifact(dokkaJar)
        }
    }
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

        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
    }
}
