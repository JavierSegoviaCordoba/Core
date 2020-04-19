plugins {
    `kotlin-dsl`
}

val androidGradleVersion = "3.6.3"
val kotlinVersion = "1.4-M1"
val safeArgsVersion = "2.2.0-rc04"
val dependencyUpdatesVersion = "0.28.0"
val detektVersion = "1.7.4"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://plugins.gradle.org/m2")
    jcenter()
    mavenCentral()
    google()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:$androidGradleVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:$safeArgsVersion")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
    implementation("com.github.ben-manes:gradle-versions-plugin:$dependencyUpdatesVersion")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.10.1")
    implementation("com.eden.orchidPlugin:com.eden.orchidPlugin.gradle.plugin:0.20.0")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
