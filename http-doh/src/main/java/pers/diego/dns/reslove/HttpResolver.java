package pers.diego.dns.reslove;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(HttpResolver.class.getName());

    @Setter
    private  int connectTimeout;

    private final int readTimeout = 10000;


    @Override
    public void close() throws Exception {

    }


    @Override
    public Packet resolve(Packet request,URL url) throws IOException {
        Packet response = null;
        try {
            response = getResponse(request, url);
        } catch (IOException e){
            logger.error("connection error, retry: " + request.getQuestions().toString());
            response = getResponse(request,url);
        }
        return response;

    }

    @Override
    public Packet resolve(Packet packet, String address, int port) throws IOException {
        URL url = new URL("https", address, String.valueOf(port));
        return resolve(packet,url);
    }

    public Packet getResponse(Packet request,URL url) throws IOException {
        HttpURLConnection conn;
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/dns-message");
        conn.setRequestProperty("Accept", "application/dns-message");
        OutputStream os = conn.getOutputStream();
        os.write(request.getBuf().array());
        os.flush();
        InputStream is = conn.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(is);
        int length = conn.getContentLength();
        return Util.readPacket(length, dataInputStream);
    }
}
