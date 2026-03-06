configurations.all {
    resolutionStrategy {
        force(libs.fabric.loader)
    }
}

plugins {
    id("turtlshell.platform-conventions")
    id("turtlshell.publish-conventions")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val generatedResources = file("src/generated/resources")

sourceSets.main {
    resources {
        srcDir(generatedResources)
    }
}

repositories {
    mavenLocal()
    maven("https://api.modrinth.com/maven")
    maven("https://maven.terraformersmc.com/")
    maven("https://maven.isxander.dev/releases")
}

dependencies {
    implementation(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    bundle(project(path = ":common", configuration = "transformProductionFabric")) {
        isTransitive = false
    }
    modImplementation(libs.fabric.loader)
    modApi(libs.fabric.api)
    modApi(libs.bundles.fabric)

    modCompileOnly(libs.bundles.common.integrations.compileOnly) {
        isTransitive = false
    }

    modRuntimeOnly(libs.bundles.fabric.integrations.runtimeOnly)

    modImplementation(libs.bundles.fabric.integrations.implementation)

    include(libs.fabric.kotlin)
}

tasks {
    // The AW file is needed in :fabric project resources when the game is run.
    val copyAccessWidener by registering(Copy::class) {
        from(loom.accessWidenerPath)
        into(generatedResources)
    }

    processResources {
        dependsOn(copyAccessWidener)

        inputs.property("mod_id", project.property("mod_id"))
        inputs.property("mod_display_name", project.property("mod_display_name"))
        inputs.property("mod_author", project.property("mod_author"))
        inputs.property("mod_description", project.property("mod_description"))

        inputs.property("version", rootProject.version)
        inputs.property("fabric_loader_version", libs.fabric.loader.get().version)
        inputs.property("fabric_api_version", libs.fabric.api.get().version)
        inputs.property("minecraft_version", rootProject.property("mc_version").toString())
        inputs.property("java_version", rootProject.property("java_version").toString())

        filesMatching("fabric.mod.json") {
            expand(
                "mod_id" to project.property("mod_id"),
                "mod_display_name" to project.property("mod_display_name"),
                "mod_author" to project.property("mod_author"),
                "mod_description" to project.property("mod_description"),
                "version" to rootProject.version,
                "fabric_loader_version" to libs.fabric.loader.get().version,
                "fabric_api_version" to libs.fabric.api.get().version,
                "minecraft_version" to rootProject.property("mc_version").toString(),
                "java_version" to rootProject.property("java_version").toString()
            )
        }
    }

    sourcesJar {
        dependsOn(copyAccessWidener)
    }
}
