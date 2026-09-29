package com.example.bedrockvoice.paper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerTracker {
    private final JavaPlugin plugin;

    public PlayerTracker(JavaPlugin plugin) { this.plugin = plugin; }

    public void start() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                // Future: publish world + XYZ to the voice server.
                player.getLocation();
            });
        }, 20L, 5L);
    }
}
