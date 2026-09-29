package com.example.bedrockvoice.paper;

import org.bukkit.plugin.java.JavaPlugin;

public final class BedrockJavaVoicePlugin extends JavaPlugin {
    private VoiceBridgeClient bridge;

    @Override public void onEnable() {
        saveDefaultConfig();
        bridge = new VoiceBridgeClient(this);
        bridge.start();
        getCommand("voice").setExecutor(new VoiceCommand(this));
        getLogger().info("BedrockJavaVoice enabled.");
    }

    @Override public void onDisable() {
        if (bridge != null) bridge.stop();
    }

    public VoiceBridgeClient bridge() { return bridge; }
}
