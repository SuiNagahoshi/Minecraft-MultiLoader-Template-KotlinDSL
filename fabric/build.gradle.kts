// gradle.properties の値を Kotlin DSL で受け取る
val minecraft_version: String by project
val parchment_minecraft: String by project
val parchment_version: String by project
val fabric_loader_version: String by project
val fabric_version: String by project
val mod_id: String by project

plugins {
    id("multiloader-loader")
    id("fabric-loom")
    kotlin("jvm") version "2.0.0" // Kotlin ソース対応
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")

    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$parchment_minecraft:$parchment_version@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:$fabric_loader_version")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.11.0+kotlin.2.0.0")

    implementation("com.google.code.findbugs:jsr305:3.0.1")
    implementation(project(":common"))
}

loom {
    val accessWidener = project(":common").file("src/main/resources/$mod_id.accesswidener")
    if (accessWidener.exists()) {
        accessWidenerPath.set(accessWidener)
    }

    mixin {
        defaultRefmapName.set("$mod_id.refmap.json")
    }

    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}
