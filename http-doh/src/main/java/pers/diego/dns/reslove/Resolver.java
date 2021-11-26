package pers.diego.dns.reslove;

import pers.diego.dns.dto.Packet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kang.zhang
 * @date 2021/11/26 18:00
 */
public interface Resolver extends AutoCloseable{

    CompletableFuture<Packet> resolveAsync(final Packet request, final ThreadPoolExecutor threadPoolExecutor);

    /**
     * This must block on resolving the given query
     * @param request
     * @return
     * @throws Exception
     */
    Packet resolve(final Packet request) throws IOException;
}
