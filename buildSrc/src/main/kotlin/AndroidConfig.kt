import com.android.build.gradle.BaseExtension

fun BaseExtension.defaultConfig() {
    compileSdkVersion(29)

    sourceSets {
        getByName("main") {
            java.srcDir("src/androidMain/kotlin")
            res.srcDir("src/androidMain/res")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
        getByName("test") {
            java.srcDir("src/androidTest/kotlin")
            res.srcDir("src/androidTest/res")
            manifest.srcFile("src/androidTest/AndroidManifest.xml")
        }
    }

    @Suppress("UnstableApiUsage")
    buildFeatures.viewBinding = true
}
