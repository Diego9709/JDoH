package pers.diego.dns.reslove;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.diego.dns.dto.Packet;
import pers.diego.dns.componment.UdpClient;

import java.io.IOException;
import java.net.URL;

/**
 * @author kang.zhangnsns
 * @date 2021/11/27 15:59
 */
@Component
public class UdpResolver implements Resolver {
    private UdpClient udpClient;

    @Autowired
    public void setUdpClient(UdpClient udpClient) {
        this.udpClient = udpClient;
    }

    @Override
    public Packet resolve(Packet request, URL url) throws IOException {
        return null;
    }

    @Override
    public Packet resolve(final Packet request, String address, int port) throws IOException {

        Packet packet = udpClient.sendPacket(request, address, port);

        return packet;
    }

    @Override
    public void close() throws Exception {

    }
}
