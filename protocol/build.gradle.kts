plugins {
    id("java")
}
group = "com.example.bedrockvoice"
version = "0.1.0"
java { toolchain.languageVersion.set(JavaLanguageVersion.of(21)) }
repositories { mavenCentral() }
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
}
tasks.test { useJUnitPlatform() }
