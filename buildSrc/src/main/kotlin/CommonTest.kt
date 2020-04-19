import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

val KotlinDependencyHandler.commonTestDependencies: List<String>
    get() = with(libs) {
        listOf(
            kotlinTestCommon,
            kotlinTestAnnotationsCommon
        )
    }