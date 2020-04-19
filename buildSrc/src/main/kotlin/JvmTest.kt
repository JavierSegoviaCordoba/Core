import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

val KotlinDependencyHandler.jvmTestDependencies: List<String>
    get() = with(libs) {
        listOf(
            junitJupiterApi,
            junitJupiterEngine,
            kotestRunnerJunit5Jvm,
            kotestAssertionsCoreJvm
        )
    }
