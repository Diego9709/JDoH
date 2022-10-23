package pers.diego.dns.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author kang.zhang
 * @date 1/31/2022 6:07 PM
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private UdpServer udpServer;

    @Autowired
    public void setUdpServer(UdpServer udpServer) {
        this.udpServer = udpServer;
    }



    @Override
    public void run(String... args) throws Exception {
        udpServer.listen();
    }
}
