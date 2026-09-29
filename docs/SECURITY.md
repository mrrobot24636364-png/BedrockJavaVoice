# Security checklist

Development token authentication is intentionally simple.

Production should add:
- TLS (`wss://`)
- short-lived session tokens
- server-side identity binding
- replay protection / sequence validation
- maximum packet size
- rate limits
- connection limits
- mute/block controls
- no client-controlled Minecraft coordinates
