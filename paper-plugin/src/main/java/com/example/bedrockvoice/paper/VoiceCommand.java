package com.example.bedrockvoice.paper;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class VoiceCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    public VoiceCommand(JavaPlugin plugin) { this.plugin = plugin; }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("status")) {
            sender.sendMessage("§aBedrockJavaVoice §7v" + plugin.getPluginMeta().getVersion());
            sender.sendMessage("§7Bridge connected: §f" + ((BedrockJavaVoicePlugin)plugin).bridge().isConnected());
            sender.sendMessage("§7Distance: §f" + plugin.getConfig().getDouble("proximity.max-distance"));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage("§aVoice config reloaded.");
            return true;
        }
        sender.sendMessage("§e/voice status");
        sender.sendMessage("§e/voice reload");
        return true;
    }
}
