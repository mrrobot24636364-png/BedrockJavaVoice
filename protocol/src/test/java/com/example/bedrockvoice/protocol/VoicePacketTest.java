package com.example.bedrockvoice.protocol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VoicePacketTest {
    @Test void roundTrip() {
        VoicePacket a = new VoicePacket(1, PacketType.AUDIO_PCM16, 7L, "player", new byte[]{1,2,3});
        VoicePacket b = VoicePacket.decode(a.encode());
        assertEquals(a.version(), b.version());
        assertEquals(a.type(), b.type());
        assertEquals(a.sequence(), b.sequence());
        assertEquals(a.senderId(), b.senderId());
        assertArrayEquals(a.payload(), b.payload());
    }
}
