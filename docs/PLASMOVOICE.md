# Plasmo Voice integration notes

The supplied `plasmovoice-fabric-1.21.11-2.1.17.jar` contains Fabric-side classes and native Opus libraries.

It is a Fabric mod and should not be placed in a Paper `plugins` directory.

Before coding an adapter, confirm:
- server loader: Fabric or Paper
- Minecraft version: 1.21.11 in the supplied JAR
- Plasmo Voice version
- exact public API artifacts for that loader/version

The bridge should target public API interfaces, not private/internal classes extracted from the mod JAR.
