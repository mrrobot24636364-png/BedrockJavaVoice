package com.example.bedrockvoice.protocol;

public enum PacketType {
    HELLO(1), AUDIO_PCM16(2), AUDIO_OPUS(3), PTT_START(4), PTT_STOP(5), PING(6), PONG(7);

    public final int id;
    PacketType(int id) { this.id = id; }

    public static PacketType fromId(int id) {
        for (PacketType t : values()) if (t.id == id) return t;
        throw new IllegalArgumentException("Unknown packet type: " + id);
    }
}
