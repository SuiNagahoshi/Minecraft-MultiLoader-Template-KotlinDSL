import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("multiloader-common");
    id("net.neoforged.moddev.legacyforge");
    kotlin("jvm") version "2.0.0";
}

legacyForge {
    mcpVersion = project.property("minecraft_version") as String;
    if (file("src/main/resources/META-INF/accesstransformer.cfg").exists()) {
        accessTransformers.from(
            files("src/main/resources/META-INF/accesstransformer.cfg")
        )
    }
    parchment {
        minecraftVersion = project.property("parchment_minecraft") as String;
        mappingsVersion = project.property("parchment_version") as String;
    }
}

dependencies {
    implementation(kotlin("stdlib"));
    compileOnly("org.spongepowered:mixin:0.8.5");
    implementation("com.google.code.findbugs:jsr305:3.0.1");
}

configurations {
    create("commonJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    create("commonResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

artifacts {
    add("commonJava", sourceSets.main.get().java.sourceDirectories.singleFile);
    add("commonResources", sourceSets.main.get().resources.sourceDirectories.singleFile);
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
}