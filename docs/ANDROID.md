# Android audio

`MicrophoneCapture` uses:
- 48,000 Hz
- mono
- PCM 16-bit
- 20 ms frames

That is a convenient frame size for an Opus encoder.

Before release:
- request RECORD_AUDIO at runtime
- use a foreground service if microphone capture must continue while Minecraft is foreground/background
- show a persistent microphone indicator/notification
- handle Bluetooth headsets and audio focus
- stop capture on disconnect
