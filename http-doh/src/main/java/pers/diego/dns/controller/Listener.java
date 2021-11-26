package pers.diego.dns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.diego.dns.service.DnsQueryService;
import java.util.Base64;

/**
 * @author kang.zhang
 * @date 2021/11/25 14:47
 */

@RestController
public class Listener {
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();

    private DnsQueryService dnsQueryService;

    @Autowired
    public void setDnsQueryService(DnsQueryService dnsQueryService) {
        this.dnsQueryService = dnsQueryService;
    }

    @RequestMapping(value = "/dns-query", method = {RequestMethod.POST, RequestMethod.PUT})
    public HttpEntity<byte[]> dnsQueryPost(final HttpEntity<byte[]> request) throws Exception {
        return dnsQuery(request.getBody());
    }

    @RequestMapping(value = "/dns-query", method = {RequestMethod.GET})
    public HttpEntity<byte[]> dnsQueryGet(@RequestParam("dns") final String dns) throws Exception {
        return dnsQuery(decoder.decode(dns));
    }

    private HttpEntity<byte[]> dnsQuery(final byte[] request) throws Exception {
        return dnsQueryService.query(request);
    }
}
