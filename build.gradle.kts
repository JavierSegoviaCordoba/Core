import tasks.baseKotlinOptions

plugins {
    id("io.gitlab.arturbosch.detekt")
}

allprojects {
    tasks {
        withType<Delete> { delete(buildDir) }
        baseKotlinOptions
    }
}

tasks {
    withType<Test> {
        // ToFix MockWebServer with gradle parallel tests execution
        // maxParallelForks = Runtime.getRuntime().availableProcessors()
        useJUnitPlatform()
        useTestNG()
        testLogging {
            setExceptionFormat("full")
            events("passed", "skipped", "failed")
        }
    }
}

dependencies {
    detektPlugins(libs.detektFormatting)
}

detekt {
    toolVersion = libs.detektFormatting.split(":").last()
    ignoreFailures = true
    autoCorrect = true
}
