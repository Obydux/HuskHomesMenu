plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.6.1"
    id("net.neoforged.licenser") version "0.7.5"
    id("org.ajoberstar.grgit") version "5.3.3"
}

group = "win.obydux"
version = "${extra["plugin_version"]}${versionMetadata()}"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.minebench.de/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.william278.net/releases/")
    maven("https://repo.william278.net/snapshots/")
    maven("https://repo.obydux.win/snapshots/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:26.1.0")
    compileOnly("net.william278.huskhomes:huskhomes-bukkit:4.11-ab42970")

    implementation("de.themoep:inventorygui:1.6.6-SNAPSHOT")
    implementation("org.apache.commons:commons-text:1.15.0")
    implementation("de.themoep:minedown-adventure:1.7.6-SNAPSHOT")
    implementation("dev.dejvokep:boosted-yaml:1.3.7")
    implementation("net.wesjd:anvilgui:2.0.4-SNAPSHOT")
    implementation("net.william278:annotaml:2.0.7-4f14c61")
    implementation("net.william278:desertwell:2.0.6-cbab27c")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.1.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.1.1")
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.processResources {
    val props = mapOf(
        "version" to version,
    )
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }

    relocate("org.apache.commons.io", "pro.obydux.huskhomes.gui.libraries.commons.io")
    relocate("org.apache.commons.text", "pro.obydux.huskhomes.gui.libraries.commons.text")
    relocate("org.apache.commons.lang3", "pro.obydux.huskhomes.gui.libraries.commons.lang3")
    relocate("org.jetbrains", "pro.obydux.huskhomes.gui.libraries")
    relocate("org.intellij", "pro.obydux.huskhomes.gui.libraries")
    relocate("de.themoep.inventorygui", "pro.obydux.huskhomes.gui.libraries.inventorygui")
    relocate("de.themoep.minedown", "pro.obydux.huskhomes.gui.libraries.minedown")
    relocate("net.wesjd.anvilgui", "pro.obydux.huskhomes.gui.libraries.anvilgui")
    relocate("net.william278.annotaml", "pro.obydux.huskhomes.gui.libraries.annotaml")
    relocate("net.william278.desertwell", "pro.obydux.huskhomes.gui.libraries.desertwell")
    relocate("dev.dejvokep", "pro.obydux.huskhomes.gui.libraries.boostedyaml")

    destinationDirectory.set(file("$rootDir/target"))
    archiveClassifier.set("")
}

tasks.jar {
    dependsOn("shadowJar")
}

tasks.test {
    useJUnitPlatform()
}

license {
    setHeader(project.file("HEADER"))
    include("**/*.java")
    newLine(true)
}

logger.lifecycle("Building HuskHomesMenu ${version} by William278 & Obydux")

@SuppressWarnings("GrMethodMayBeStatic")
fun versionMetadata(): String {
    // Get if there is a tag for this commit
    val tag = grgit.tag.list().find { it.commit.id == grgit.head().id }
    if (tag != null) {
        return ""
    }

    // Otherwise, get the last commit hash and if it's a clean head
    if (grgit == null) {
        val runNumber = System.getenv("GITHUB_RUN_NUMBER")
        return "-" + if (runNumber != null) "build.$runNumber" else "unknown"
    }

    val headId = grgit.head().abbreviatedId
    val isClean = grgit.status().isClean()
    return "-$headId" + if (isClean) "" else "-indev"
}