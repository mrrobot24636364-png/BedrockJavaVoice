# BedrockJavaVoice Expanded

A larger starter monorepo for Android Bedrock voice -> Java Minecraft.

## Modules

- `protocol`: shared packet model and binary framing.
- `android-app`: Kotlin Android companion app with microphone capture abstraction, WebSocket transport, PTT UI, and settings.
- `voice-server`: standalone Java WebSocket relay with sessions, authentication hooks, packet routing, and proximity calculation.
- `paper-plugin`: Paper 1.21.x plugin skeleton with commands, config, player tracking, and voice-server client.

## Important

This build is an engineering scaffold. The protocol and transport are implemented, but production-grade Opus negotiation, encryption, replay protection, and Plasmo Voice API integration still need to be wired to the exact server platform/version you use.

The uploaded `plasmovoice-fabric-1.21.11-2.1.17.jar` is a Fabric mod. It is NOT a Paper plugin. Do not put that JAR in `plugins/`.

## Build

The repository is configured for Gradle. In a normal Git checkout with a Gradle installation:

```bash
gradle :android-app:assembleDebug
gradle :paper-plugin:build
gradle :voice-server:build
```

GitHub Actions uses the same project and uploads APK/JAR artifacts.

## Test server

```bash
java -jar voice-server/build/libs/voice-server-0.1.0-all.jar 8080
```

Android can connect to `ws://HOST:8080`.

## Protocol

Text:
- `HELLO <player-id> <token>`
- `PTT_START`
- `PTT_STOP`
- `PING`

Binary:
- version byte
- packet type
- sequence
- sender id length + UTF-8 sender id
- payload length
- payload

Packet type `AUDIO_PCM16` is a development packet. For production, replace it with Opus frames.

## Next integration

1. Pick Fabric+Plasmo Voice OR Paper+Plasmo Voice.
2. Implement the exact Plasmo Voice API adapter for that platform.
3. Add Opus encoding on Android.
4. Add Opus decode/playback on Android.
5. Add authenticated player identity through Geyser/Floodgate.
6. Route audio by Minecraft world and distance.
