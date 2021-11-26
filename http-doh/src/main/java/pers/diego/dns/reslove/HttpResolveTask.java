package pers.diego.dns.reslove;

import lombok.Setter;
import pers.diego.dns.dto.Packet;
import pers.diego.dns.exceptions.ConnectionErrorException;
import pers.diego.dns.exceptions.ErrorType;
import pers.diego.dns.util.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.*;

/**
 * @author kang.zhang
 * @date 2021/11/26 19:27
 */

@Setter
public class HttpResolveTask implements Resolver {
    private  int connectTimeout;
    private  int readTimeout = 4000;
    private  URL url;
    private  Packet packet;


    @Override
    public void close() throws Exception {

    }




    @Override
    public CompletableFuture<Packet> resolveAsync(Packet request, ThreadPoolExecutor threadPoolExecutor) {
        CompletableFuture<Packet> ret = new CompletableFuture<>();
        threadPoolExecutor.submit(() -> {
            try{
                ret.complete(resolve(request));
            }catch (Throwable e){
                ret.completeExceptionally(e);
            }
        });
        return ret;
    }

    @Override
    public Packet resolve(Packet request) throws IOException {
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
            os.write(packet.getBuf().array());
            os.flush();
            InputStream is = conn.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(is);
            int length = conn.getContentLength();
            return Util.readPacket(length, dataInputStream);
        }
    }

}
