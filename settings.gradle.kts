pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
			name="Simple Discord Link"
			url=maven("https://maven.firstdark.dev/releases")
		}
		mavenCentral()
		gradlePluginPortal()
	}
	dependencies {
    // Keep your existing dependencies for minecraft, mappings, and fabric-loader

    // Inject Simple Discord Link and CraterLib using your exact versions
    implementation("com.hypherionmc.sdlink:sdlink:3.4.0")
    modImplementation("com.hypherionmc.craterlib:CraterLib-Fabric-1.21:3.1.0")
}
	plugins {
		id("net.fabricmc.fabric-loom-remap") version providers.gradleProperty("loom_version")
	}
}

// Should match your modid
rootProject.name = "tamilcraft-discord-commands"
