plugins {
    id("java")
    id("com.github.johnrengelman.shadow")
}
group = "com.example.bedrockvoice"
version = "0.1.0"
java { toolchain.languageVersion.set(JavaLanguageVersion.of(21)) }
repositories { maven("https://repo.papermc.io/repository/maven-public/") }
dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    implementation(project(":protocol"))
    implementation("org.java-websocket:Java-WebSocket:1.6.0")
}
tasks {
    build { dependsOn(shadowJar) }
    shadowJar { archiveClassifier.set("") }
}
