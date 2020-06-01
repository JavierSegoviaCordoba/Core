plugins {
    id("maven-publish")
    signing
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

val isCoreReleaseEnv: Boolean? = System.getenv("isCoreReleaseEnv")?.toBoolean()
val isCoreRelease: String by project

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("Core")
            description.set("Core multiplatform")
            url.set("http://github.com/JavierSegoviaCordoba/Core")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("JavierSegoviaCordoba")
                    name.set("Javier Segovia Cordoba")
                    email.set("javiersegoviacordoba@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/JavierSegoviaCordoba/Core")
                connection.set("scm:git:https://github.com/JavierSegoviaCordoba/Core.git")
                developerConnection.set("scm:git:git@github.com:JavierSegoviaCordoba/Core.git")
            }
        }
        repositories {
            val releasesRepo = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            val snapshotsRepo = "https://oss.sonatype.org/content/repositories/snapshots"

            val isRelease = isCoreReleaseEnv ?: isCoreRelease.toBoolean()

            maven(if (isRelease) releasesRepo else snapshotsRepo) {
                credentials {
                    username = System.getenv("ossUser")
                    password = System.getenv("ossToken")
                }
            }
        }
    }
}
