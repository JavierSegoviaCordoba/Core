plugins {
    id("com.eden.orchidPlugin")
}

dependencies {
    orchidImplementation("io.github.javaeden.orchid:OrchidDocs:0.20.0")
    orchidImplementation("io.github.javaeden.orchid:OrchidKotlindoc:0.20.0")
    orchidImplementation("io.github.javaeden.orchid:OrchidPluginDocs:0.20.0")
    orchidImplementation("io.github.javaeden.orchid:OrchidGithub:0.20.0")
    orchidImplementation("io.github.javaeden.orchid:OrchidCopper:0.20.0")
    orchidImplementation("io.github.javaeden.orchid:OrchidBsDoc:0.20.0")
}

orchid {
    theme = "BsDoc"
    baseUrl = "https://username.github.io/project"
    version = "1.0.0"
    args = mutableListOf("--experimentalSourceDoc")
}
