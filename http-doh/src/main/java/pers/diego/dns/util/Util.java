package pers.diego.dns.util;

import pers.diego.dns.dto.Packet;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author kang.zhang
 * @date 2021/11/26 20:50
 */
public class Util {
    public static Packet readPacket(final int length, final DataInputStream dis) throws IOException {
        final ByteBuffer bb = ByteBuffer.allocate(length);
        final byte[] request = bb.array();
        dis.readFully(request);
        return new Packet(bb);
    }
}
