package com.example.bedrockvoice.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class VoiceServer extends WebSocketServer {
    private final Map<WebSocket, ClientSession> sessions = new ConcurrentHashMap<>();
    private final String token;
    private final ProximityRouter router;

    public VoiceServer(int port, String token, double maxDistance) {
        super(new InetSocketAddress("0.0.0.0", port));
        this.token = token;
        this.router = new ProximityRouter(maxDistance);
    }

    @Override public void onOpen(WebSocket conn, ClientHandshake handshake) {
        sessions.put(conn, new ClientSession(conn));
        conn.send("VOICE_SERVER_READY");
    }

    @Override public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        sessions.remove(conn);
    }

    @Override public void onMessage(WebSocket conn, String msg) {
        ClientSession s = sessions.get(conn);
        if (s == null) return;

        if (msg.startsWith("HELLO ")) {
            String[] p = msg.split(" ", 3);
            if (p.length == 3 && p[2].equals(token)) {
                s.playerId(p[1]);
                s.authenticated(true);
                conn.send("WELCOME " + s.playerId());
            } else {
                conn.close(1008, "invalid token");
            }
        } else if (msg.equals("PTT_START")) {
            if (s.authenticated()) s.talking(true);
        } else if (msg.equals("PTT_STOP")) {
            s.talking(false);
        } else if (msg.equals("PING")) {
            conn.send("PONG");
        }
    }

    @Override public void onMessage(WebSocket conn, java.nio.ByteBuffer data) {
        ClientSession source = sessions.get(conn);
        if (source == null || !source.authenticated() || !source.talking()) return;

        for (ClientSession target : sessions.values()) {
            if (target == source || !target.authenticated()) continue;
            if (router.canHear(source, target) && target.socket().isOpen()) {
                target.socket().send(data);
            }
        }
    }

    @Override public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override public void onStart() {
        System.out.println("Voice server listening on " + getAddress());
    }
}
