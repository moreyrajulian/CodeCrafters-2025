plugins {
    id("java")
}

group = "CodeCrafters"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("javazoom:jlayer:1.0.1")
}

tasks.test {
    useJUnitPlatform()
}