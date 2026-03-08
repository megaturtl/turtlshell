import utilities.VersionType
import utilities.writeVersion

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("dev.architectury.loom")
    id("net.nemerosa.versioning")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        maven("https://maven.turl.cc/releases") {
            name = "Turtl-Public"
            credentials {
                username = System.getenv("MAVEN_USER")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }

//        maven {
//            val snapshot = project.isSnapshot()
//
//            val releases = uri("https://artefacts.turtl.cc/releases")
//            val snapshots = uri("https://artefacts.turtl.cc/snapshots")
//
//            url = if (snapshot) snapshots else releases
//            name = "Reposilite.${if (snapshot) "Snapshots" else "Releases"}"
//            credentials {
//                username = System.getenv("REPOSILITE_USERNAME")
//                password = System.getenv("REPOSILITE_PASSWORD")
//            }
//        }
    }

    publications {
        create<MavenPublication>(project.name) {
            artifact(tasks.remapJar)
            artifact(tasks.remapSourcesJar)

            @Suppress("UnstableApiUsage")
            loom.disableDeprecatedPomGeneration(this)

            groupId = "cc.turtl"
            artifactId = project.findProperty("maven.artifactId")?.toString() ?: project.name
            version = project.writeVersion(VersionType.PUBLISHING)
            pom {
                properties = mapOf(
                    "gitCommit" to versioning.info.commit
                )
            }
        }
    }
}