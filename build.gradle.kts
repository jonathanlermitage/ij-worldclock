plugins {
    id("java")
    id("idea")
    id("org.jetbrains.intellij") version "1.1.6" // https://github.com/JetBrains/gradle-intellij-plugin
}

// Import variables from gradle.properties file
val pluginIdeaVersion: String by project
val pluginDownloadIdeaSources: String by project
val pluginInstrumentPluginCode: String by project
val pluginVersion: String by project
val pluginJavaVersion: String by project
val pluginEnableBuildSearchableOptions: String by project

val inCI = System.getenv("CI") != null

logger.quiet("Will use IDEA $pluginIdeaVersion and Java $pluginJavaVersion")

group = "lermitage.intellij.worldclock"
version = pluginVersion

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
}

intellij {
    downloadSources.set(pluginDownloadIdeaSources.toBoolean() && !inCI)
    pluginName.set("WorldClock")
    sandboxDir.set("${rootProject.projectDir}/.idea-sandbox/${pluginIdeaVersion}")
    updateSinceUntilBuild.set(false)
    version.set(pluginIdeaVersion)
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = pluginJavaVersion
        targetCompatibility = pluginJavaVersion
        options.compilerArgs = listOf("-Xlint:deprecation")
    }
    withType<Test> {
        useJUnitPlatform()
    }
    runIde {
        jvmArgs = listOf("-Xms768m", "-Xmx2048m", "--add-exports", "java.base/jdk.internal.vm=ALL-UNNAMED")
    }
    buildSearchableOptions {
        enabled = pluginEnableBuildSearchableOptions.toBoolean()
        jvmArgs = listOf("--add-exports", "java.base/jdk.internal.vm=ALL-UNNAMED")
    }
}
