import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

val KotlinDependencyHandler.androidMainDependencies: List<String>
    get() = with(libs) {
        listOf(
            appcompat,
            lifecycleCommonJava8,
            lifecycleRuntime,
            lifecycleLivedataKtx,
            activityKtx,
            fragmentKtx
        )
    }