plugins {
    id("io.codearte.nexus-staging")
}

nexusStaging {
    username = System.getenv("ossUser")
    password = System.getenv("ossToken")
    packageGroup = "com.javiersc"
    numberOfRetries = 50
    delayBetweenRetriesInMillis = 3000
}
