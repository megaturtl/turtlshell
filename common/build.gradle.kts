
import utilities.isSnapshot
import utilities.version
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

plugins {
    id("turtlshell.base-conventions")
    id("turtlshell.publish-conventions")

    id("net.kyori.blossom")
    id("org.jetbrains.gradle.plugin.idea-ext")
}

architectury {
    common("neoforge", "fabric")
}

repositories {
    maven("https://api.modrinth.com/maven")
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.gegy.dev")
    maven("https://maven.isxander.dev/releases")
    mavenLocal()
}

dependencies {
    implementation(libs.bundles.kotlin)
    modImplementation(libs.fabric.loader)

    modCompileOnly(libs.bundles.common.integrations.compileOnly) {
        isTransitive = false
    }


    // Unit Testing
    testImplementation(libs.bundles.unitTesting)
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        setEvents(listOf("failed"))
        setExceptionFormat("full")
    }
}

sourceSets {
    main {
        blossom {
            kotlinSources {
                fun generateLicenseHeader() : String {
                    val builder = StringBuilder()
                    builder.append("/*\n")
                    rootProject.file("HEADER").forEachLine {
                        if(it.isEmpty()) {
                            builder.append(" *").append("\n")
                        } else {
                            builder.append(" * ").append(it).append("\n")
                        }
                    }

                    return builder.append(" */").append("\n").toString()
                }

                property("license", generateLicenseHeader())
                property("modid", "turtlshell")
                property("version", project.version())
                property("isSnapshot", if(rootProject.isSnapshot()) "true" else "false")
                property("gitCommit", versioning.info.commit)
                property("branch", versioning.info.branch)
                System.getProperty("buildNumber")?.let { property("buildNumber", it) }
                property("timestamp", OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss")) + " UTC")
            }
        }
    }
}

