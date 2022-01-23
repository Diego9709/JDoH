package pers.diego.dns.componment;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.diego.dns.dto.Packet;
import pers.diego.dns.mangager.QueryDispatcherManager;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.Executor;

/**
 * @author kang.zhang
 * @date 1/23/2022 6:37 PM
 */
@Component
public class UdpServer {
    @Value("${dns.listen.port}")
    private int port;

    private QueryDispatcherManager queryDispatcherManager;

    private Executor executor;

    private static final Logger logger = LoggerFactory.getLogger(UdpServer.class);

    @Autowired
    public void setQueryDispatcherManager(QueryDispatcherManager queryDispatcherManager) {
        this.queryDispatcherManager = queryDispatcherManager;
    }

    @Autowired
    public void setExecutor(@Qualifier("threadPoolExecutor") Executor executor) {
        this.executor = executor;
    }

    @PostConstruct
    public void listen() throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        while(true){
            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
            socket.receive(request);
            Handler handler = new Handler(socket,request);
            executor.execute(handler);
        }

    }


    class Handler implements Runnable{
        private DatagramPacket datagramPacket;

        private DatagramSocket socket;

        public Handler(DatagramSocket socket, DatagramPacket datagramPacket){
            this.socket = socket;
            this.datagramPacket = datagramPacket;

        }
        @Override
        public void run() {
            Packet packet = new Packet(datagramPacket.getData());
            logger.info("new dns query: " + packet);
            try {
                byte[] response = queryDispatcherManager.dispatchUdpQuery(datagramPacket.getData());
                DatagramPacket ans = new DatagramPacket(response,response.length,datagramPacket.getAddress(),datagramPacket.getPort());
                socket.send(ans);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }

        }
    }
}
