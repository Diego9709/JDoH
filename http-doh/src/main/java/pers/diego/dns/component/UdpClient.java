package pers.diego.dns.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pers.diego.dns.dto.Packet;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

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
        ByteBuffer response = ByteBuffer.allocate(1024);
        DatagramPacket datagramPacket = new DatagramPacket(packet.copyRaw(), packet.copyRaw().length,inetAddress,port);
        DatagramPacket responsePacket = new DatagramPacket(response.array(),response.array().length);
        datagramSocket.send(datagramPacket);
        try{
            datagramSocket.receive(responsePacket);
        } catch (SocketTimeoutException e) {
            datagramSocket.send(datagramPacket);
        }
        datagramSocket.close();
        return new Packet(responsePacket.getData());

    }
}
