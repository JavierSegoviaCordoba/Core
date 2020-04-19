import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("org.jetbrains.dokka")
}

tasks {
    val dokka by getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"

        multiplatform {
            val global by creating { }
            val common by creating { }
            val jvm by creating { }
            val android by creating { }
        }
    }
}
