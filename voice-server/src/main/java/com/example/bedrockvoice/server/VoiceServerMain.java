package com.example.bedrockvoice.server;

public final class VoiceServerMain {
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        String token = args.length > 1 ? args[1] : "dev-token";
        double distance = args.length > 2 ? Double.parseDouble(args[2]) : 32.0;
        new VoiceServer(port, token, distance).start();
    }
}
