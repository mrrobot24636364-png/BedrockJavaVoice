package com.example.bedrockvoice.server;

import org.java_websocket.WebSocket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProximityRouterTest {
    @Test void sameWorldAndNearCanHear() {
        ClientSession a = new ClientSession((WebSocket)null);
        ClientSession b = new ClientSession((WebSocket)null);
        a.authenticated(true); b.authenticated(true);
        a.position("world", 0,0,0); b.position("world", 10,0,0);
        assertTrue(new ProximityRouter(32).canHear(a,b));
    }
    @Test void differentWorldCannotHear() {
        ClientSession a = new ClientSession((WebSocket)null);
        ClientSession b = new ClientSession((WebSocket)null);
        a.authenticated(true); b.authenticated(true);
        a.position("world", 0,0,0); b.position("nether", 1,0,0);
        assertFalse(new ProximityRouter(32).canHear(a,b));
    }
}
