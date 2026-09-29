package com.example.bedrockvoice.server;

public final class ProximityRouter {
    private final double maxDistance;

    public ProximityRouter(double maxDistance) { this.maxDistance = maxDistance; }

    public boolean canHear(ClientSession a, ClientSession b) {
        if (!a.authenticated() || !b.authenticated()) return false;
        if (!a.world().equals(b.world())) return false;
        double dx = a.x() - b.x(), dy = a.y() - b.y(), dz = a.z() - b.z();
        return dx*dx + dy*dy + dz*dz <= maxDistance * maxDistance;
    }
}
