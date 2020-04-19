plugins {
    id("maven-publish")
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("Core")
            description.set("Core multiplatform utilities")
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
            maven("https://api.bintray.com/maven/javiersegoviacordoba/Resources/Core/;publish=1;override=1") {
                credentials {
                    username = System.getenv("bintrayUser")
                    password = System.getenv("bintrayKey")
                }
            }
        }
    }
}