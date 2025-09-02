// gradle.properties の値を Kotlin DSL で受け取る
val minecraft_version: String by project
val forge_version: String by project
val parchment_minecraft: String by project
val parchment_version: String by project
val mod_id: String by project
//import net.minecraftforge.gradle.common.util.MavenArtifactGetter // fg 用
//val fg = extensions.getByName("fg") as MavenArtifactGetter

plugins {
    id("multiloader-loader")
    id("net.neoforged.moddev.legacyforge")
    kotlin("jvm") version "2.2.0"
}

mixin {
    add(sourceSets.main.get(), "$mod_id.refmap.json")

    config("$mod_id.mixins.json")
    config("$mod_id.forge.mixins.json")
}

legacyForge {
    version = "$minecraft_version-$forge_version"

    validateAccessTransformers = true

    val atFile = project(":common").file("src/main/resources/META-INF/accesstransformer.cfg")
    if (atFile.exists()) {
        accessTransformers.from(atFile)
    }

    parchment {
        minecraftVersion = parchment_minecraft
        mappingsVersion = parchment_version
    }

    runs {
        create("client") {
            client()
        }
        create("data") {
            data()
            programArguments.addAll(
                "--mod", mod_id,
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
            )
        }
        create("server") {
            server()
        }
    }

    mods {
        create(mod_id) {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets.main.get().resources.srcDir("src/generated/resources")

dependencies {
    //minecraft("net.minecraftforge:foge:${minecraft_version}-${forge_version}")
    compileOnly(project(":common"))
    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")
    //implementation("thedarkcolour:kotlinforforge:4.11.0")
}

tasks.jar {
    finalizedBy("reobfJar")
    manifest.attributes(
        mapOf(
            "MixinConfigs" to "$mod_id.mixins.json,$mod_id.forge.mixins.json"
        )
    )
}
