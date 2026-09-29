package com.example.bedrockvoice.server;

import org.java_websocket.WebSocket;

public final class ClientSession {
    private final WebSocket socket;
    private String playerId;
    private boolean authenticated;
    private boolean talking;
    private double x, y, z;
    private String world = "world";

    public ClientSession(WebSocket socket) { this.socket = socket; }
    public WebSocket socket() { return socket; }
    public String playerId() { return playerId; }
    public void playerId(String v) { playerId = v; }
    public boolean authenticated() { return authenticated; }
    public void authenticated(boolean v) { authenticated = v; }
    public boolean talking() { return talking; }
    public void talking(boolean v) { talking = v; }
    public double x() { return x; }
    public double y() { return y; }
    public double z() { return z; }
    public String world() { return world; }

    public void position(String world, double x, double y, double z) {
        this.world = world; this.x = x; this.y = y; this.z = z;
    }
}
