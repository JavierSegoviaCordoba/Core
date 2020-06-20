import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

val KotlinDependencyHandler.jvmMainDependencies: List<String>
    get() = with(libs) {
        listOf(
            kotlinStdlibJdk8,
            mockwebserver
        )
    }
