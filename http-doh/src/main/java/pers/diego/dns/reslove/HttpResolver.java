package pers.diego.dns.reslove;

import lombok.Setter;
import org.springframework.stereotype.Component;
import pers.diego.dns.dto.Packet;
import pers.diego.dns.util.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author kang.zhang
 * @date 2021/11/26 19:27
 */
@Component

public class HttpResolver implements Resolver {
    @Setter
    private  int connectTimeout;
    private final int readTimeout = 4000;
    @Setter
    private  Packet packet;


    @Override
    public void close() throws Exception {

    }



    @Override
    public Packet resolve(Packet request,URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/dns-message");
        conn.setRequestProperty("Accept", "application/dns-message");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(request.getBuf().array());
            os.flush();
            InputStream is = conn.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(is);
            int length = conn.getContentLength();
            return Util.readPacket(length, dataInputStream);
        }
    }

    @Override
    public Packet resolve(Packet packet, String address, int port) throws IOException {
        URL url = new URL("https", address, String.valueOf(port));
        return resolve(packet,url);
    }

}
