package com.example.bedrockvoice.paper;

import org.bukkit.plugin.java.JavaPlugin;
import java.net.URI;

public final class VoiceBridgeClient {
    private final JavaPlugin plugin;
    private boolean connected;

    public VoiceBridgeClient(JavaPlugin plugin) { this.plugin = plugin; }

    public void start() {
        String host = plugin.getConfig().getString("voice-server.host", "127.0.0.1");
        int port = plugin.getConfig().getInt("voice-server.port", 8080);
        try {
            URI uri = URI.create("ws://" + host + ":" + port);
            plugin.getLogger().info("Voice bridge configured for " + uri);
            // Next implementation: connect Java player identity/session data.
            connected = false;
        } catch (Exception e) {
            plugin.getLogger().warning("Invalid voice-server configuration: " + e.getMessage());
        }
    }

    public void stop() { connected = false; }
    public boolean isConnected() { return connected; }
}
