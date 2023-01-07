package pers.diego.dns.mangager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import pers.diego.dns.bo.DomainTree;
import pers.diego.dns.component.UpstreamDoh;
import pers.diego.dns.dto.Packet;
import pers.diego.dns.reslove.HttpResolver;
import pers.diego.dns.reslove.UdpResolver;
import pers.diego.dns.util.DomainTreeUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author kang.zhang
 * @date 2021/11/30 11:44
 */

@Service
public class QueryDispatcherManager {

    private Executor executor;

    private HttpResolver httpResolver;

    private UdpResolver udpResolver;

    @Value("${dns.servers.udp.address}")
    private String  udpAddress;

    @Value("${dns.servers.udp.port}")
    private int  udpPort;


    @Value("${dns.mod}")
    private int mod;

    @Value("${dns.domain.gfwPath}")
    private String gfwPath;

    @Value("${dns.domain.cnPath}")
    private String cnPath;


    private UpstreamDoh upstreamDoh;

    private Logger logger = LoggerFactory.getLogger(QueryDispatcherManager.class.getName());

    @Autowired
    public void setUpstreamDoh(UpstreamDoh upstreamDoh) {
        this.upstreamDoh = upstreamDoh;
    }

    @Autowired
    public void setThreadPoolExecutor(@Qualifier("threadPoolExecutor") Executor executor) {
        this.executor = executor;
    }

    @Autowired
    public void setHttpResolve(HttpResolver httpResolve) {
        this.httpResolver = httpResolve;
    }

    @Autowired
    public void setUdpResolver(UdpResolver udpResolver) {
        this.udpResolver = udpResolver;
    }

    public HttpEntity<byte[]> dispatchHttpQuery(byte[] request) throws IOException {

        return new HttpEntity<>(dispatchUdpQuery(request));

    }

    public void setMod_0(){
        this.mod = 0;
    }
    public void setMod_1(){
        this.mod = 1;
    }

    public byte[] dispatchUdpQuery(byte[] request) throws IOException {

        Packet packet = new Packet(request);

        List<String> domains = packet.getQuestions().stream().map(question -> question.getName().getDomain()).collect(Collectors.toList());

        if(mod == 1){
            DomainTree gfwDomainTree = DomainTreeUtil.getGfwDomainTree(gfwPath);
            URL url = this.upstreamDoh.getUrl();
            for(String domain : domains){
                if(gfwDomainTree.include(domain)){
                    logger.info("new gfw query: " + domains + "\n");
                    Packet resolve = httpResolver.resolve(packet, url);
                    return resolve.copyRaw();
                }
            }
            logger.info("new cn query: " + domains + "\n");
            byte[] raw = udpResolver.resolve(packet, udpAddress,udpPort).copyRaw();
            return  raw;
        }else{
            DomainTree cnDomainTree = DomainTreeUtil.getCnDomainTree(cnPath);
            for(String domain : domains){
                if(cnDomainTree.include(domain)){
                    logger.info("new cn query: " + domains + "\n");
                    Packet resolve = udpResolver.resolve(packet, udpAddress,udpPort);
                    return resolve.copyRaw();
                }

            }
            logger.info("new gfw query: " + domains + "\n");
            URL url = this.upstreamDoh.getUrl();
            Packet resolve = httpResolver.resolve(packet, url);
            return  resolve.copyRaw();
        }

    }

}
