plugins {
    `maven-publish`
    alias(libs.plugins.fabric.loom)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    withSourcesJar()
}

group = "net.racialgamer.smallpop"
version = "0.4"

repositories {
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft(libs.minecraft)
    mappings(libs.yarn)
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modApi(libs.modmenu)
    modApi(libs.cloth.config)
}

tasks {
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release = 8
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching(listOf("fabric.mod.json")) {
            expand("version" to project.version)
        }
    }

    jar {
        archiveClassifier = "default"
        from("LICENSE") {
            rename { "${it}_${rootProject.name}" }
        }
    }

    defaultTasks("build")
}

publishing {
    publications {
        create<MavenPublication>("mod") {
            from(components["java"])
        }
    }
}
