java {
    sourceSets["main"].java.srcDirs("src/main/java")
    sourceSets["test"].java.srcDirs("src/test/java")
}

plugins {
    id("java")
    id("application")
}

group = "CodeCrafters"
version = "1.1.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("GameLauncher.GameLauncher")
}

dependencies {
    //testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    implementation("javazoom:jlayer:1.0.1")

    testImplementation("org.mockito:mockito-core:5.17.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.17.0")

}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-javaagent:" + configurations.testRuntimeClasspath.get().find { it.name.contains("mockito-core") }?.absolutePath)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "GameLauncher.GameLauncher"
    }

    // Esto incluye las dependencias en el .jar (fat jar)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}