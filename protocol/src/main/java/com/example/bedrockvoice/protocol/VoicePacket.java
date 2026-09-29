package com.example.bedrockvoice.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public record VoicePacket(int version, PacketType type, long sequence,
                          String senderId, byte[] payload) {
    public static final int VERSION = 1;

    public byte[] encode() {
        byte[] sender = senderId.getBytes(StandardCharsets.UTF_8);
        ByteBuffer b = ByteBuffer.allocate(1 + 1 + 8 + 2 + sender.length + 4 + payload.length)
                .order(ByteOrder.BIG_ENDIAN);
        b.put((byte) version).put((byte) type.id).putLong(sequence);
        b.putShort((short) sender.length).put(sender);
        b.putInt(payload.length).put(payload);
        return b.array();
    }

    public static VoicePacket decode(byte[] data) {
        ByteBuffer b = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN);
        int version = Byte.toUnsignedInt(b.get());
        PacketType type = PacketType.fromId(Byte.toUnsignedInt(b.get()));
        long sequence = b.getLong();
        int senderLen = Short.toUnsignedInt(b.getShort());
        byte[] sender = new byte[senderLen];
        b.get(sender);
        int payloadLen = b.getInt();
        if (payloadLen < 0 || payloadLen > b.remaining()) throw new IllegalArgumentException("Invalid payload");
        byte[] payload = new byte[payloadLen];
        b.get(payload);
        return new VoicePacket(version, type, sequence,
                new String(sender, StandardCharsets.UTF_8), payload);
    }
}
