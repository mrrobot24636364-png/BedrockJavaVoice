# Architecture

Android captures microphone PCM16 at 48 kHz mono.

Development path:
Android -> WebSocket -> Voice Server -> WebSocket clients.

Production path:
Android -> Opus -> authenticated voice relay -> Java/Plasmo adapter -> Java voice clients.

Minecraft location should be authoritative on the Java server. Do not trust coordinates sent by the phone for proximity decisions.
