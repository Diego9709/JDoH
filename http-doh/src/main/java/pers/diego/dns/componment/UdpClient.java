package pers.diego.dns.componment;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pers.diego.dns.dto.Packet;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author kang.zhang
 * @date 2021/11/27 17:24
 */
@Component
public class UdpClient {


    public Packet sendPacket(Packet packet, String address, int port) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.setSoTimeout(5000);
        InetAddress inetAddress = InetAddress.getByName(address);
        ByteBuffer response = ByteBuffer.allocate(512);
        DatagramPacket datagramPacket = new DatagramPacket(packet.getBuf().array(),packet.getLength(),inetAddress,port);
        DatagramPacket responsePacket = new DatagramPacket(response.array(),response.array().length);
        datagramSocket.send(datagramPacket);
        datagramSocket.receive(responsePacket);
        datagramSocket.close();
        ByteBuffer wrapPacket = ByteBuffer.wrap(responsePacket.getData());
        return new Packet(wrapPacket);

    }
}
