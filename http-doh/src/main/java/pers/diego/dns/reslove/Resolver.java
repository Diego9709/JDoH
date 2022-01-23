package pers.diego.dns.reslove;

import pers.diego.dns.dto.Packet;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kang.zhang
 * @date 2021/11/26 18:00
 */
public interface Resolver extends AutoCloseable{

    default CompletableFuture<Packet> resolveAsync(final Packet request, final URL url,final Executor executor){
        final CompletableFuture<Packet> ret = new CompletableFuture<>();
        executor.execute(() -> {
            try{
                ret.complete(resolve(request,url));
            }catch (Throwable e){
                ret.completeExceptionally(e);
            }
        });
        return ret;
    }

    /**
     * This must block on resolving the given query
     * @param request
     * @return
     * @throws Exception
     */
    Packet resolve(final Packet request, URL url) throws IOException;


    Packet resolve(final Packet packet, String address, int port) throws IOException;
}
