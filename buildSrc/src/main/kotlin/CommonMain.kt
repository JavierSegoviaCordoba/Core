import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

val KotlinDependencyHandler.commonMainDependencies: List<String>
    get() = with(libs) {
        listOf(
            kotlinStdlibCommon
        )
    }