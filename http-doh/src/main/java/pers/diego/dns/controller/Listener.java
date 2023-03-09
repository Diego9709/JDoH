package pers.diego.dns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.diego.dns.bo.CommonResult;
import pers.diego.dns.service.DnsQueryService;
import pers.diego.dns.service.DomainService;

import java.util.Base64;

/**
 * @author kang.zhang
 * @date 2021/11/25 14:47
 */

@RestController
public class Listener {
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();

    private DnsQueryService dnsQueryService;
    private DomainService domainService;

    @Autowired
    public void setDnsQueryService(DnsQueryService dnsQueryService) {
        this.dnsQueryService = dnsQueryService;
    }

    @Autowired
    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    @RequestMapping(value = "/dns-query", method = {RequestMethod.POST, RequestMethod.PUT})
    public HttpEntity<byte[]> dnsQueryPost(final HttpEntity<byte[]> request) throws Exception {
        return dnsQuery(request.getBody());
    }

    @RequestMapping(value = "/dns-query", method = {RequestMethod.GET})
    public HttpEntity<byte[]> dnsQueryGet(@RequestParam("dns") final String dns) throws Exception {
        return dnsQuery(decoder.decode(dns));
    }

    @RequestMapping(value = "/dns-query/add-domain", method = {RequestMethod.POST})
    public CommonResult addDomain(@RequestParam("domain") String domain) {
        return domainService.addDomain(domain);
    }

    @RequestMapping(value = "/dns-query/del-domain", method = {RequestMethod.POST})
    public CommonResult delDomain(@RequestParam("domain") String domain) {
        return domainService.delDomain(domain);
    }

    @RequestMapping(value = "/dns-query/set-mod", method = {RequestMethod.POST})
    public CommonResult setMod(@RequestParam("mod") int mod) {
        if (mod == 1){
            return dnsQueryService.setAsBlackMod();
        }else if(mod == 0){
            return dnsQueryService.setAsWhiteMod();
        }else {
            return dnsQueryService.setAsDohMod();
        }
    }
    @RequestMapping(value = "/dns-query/set-doh", method = {RequestMethod.POST})
    public CommonResult setMod(@RequestParam("url") String url) {
        return dnsQueryService.changeUpStreamDoh(url);

    }

    @RequestMapping(value = "/dns-query/set-udp", method = {RequestMethod.POST})
    public CommonResult setMod(@RequestParam("address") String address, @RequestParam(value = "port", defaultValue = "53") int port) {
        return dnsQueryService.changUpStreamUdp(address, port);
    }

    private HttpEntity<byte[]> dnsQuery(final byte[] request) throws Exception {
        return dnsQueryService.query(request);
    }
}
