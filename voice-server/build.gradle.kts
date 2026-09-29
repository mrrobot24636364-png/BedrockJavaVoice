plugins {
    id("java")
    id("com.github.johnrengelman.shadow")
}
group = "com.example.bedrockvoice"
version = "0.1.0"
java { toolchain.languageVersion.set(JavaLanguageVersion.of(21)) }
repositories { mavenCentral() }
dependencies {
    implementation(project(":protocol"))
    implementation("org.java-websocket:Java-WebSocket:1.6.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
}
tasks {
    build { dependsOn(shadowJar) }
    shadowJar {
        archiveClassifier.set("all")
        manifest { attributes["Main-Class"] = "com.example.bedrockvoice.server.VoiceServerMain" }
    }
    test { useJUnitPlatform() }
}
