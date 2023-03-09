package pers.diego.dns.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import pers.diego.dns.bo.CommonResult;
import pers.diego.dns.component.UpstreamDoh;
import pers.diego.dns.mangager.QueryDispatcherManager;
import pers.diego.dns.util.DomainTreeUtil;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @author kang.zhang
 * @date 2021/11/25 14:50
 */

@Service
public class DnsQueryService {

    public QueryDispatcherManager queryDispatcherManager;

    private Logger logger = LoggerFactory.getLogger(DnsQueryService.class.getName());

    private UpstreamDoh upstreamDoh;

    @Autowired
    public void setUpstreamDoh(UpstreamDoh upstreamDoh) {
        this.upstreamDoh = upstreamDoh;
    }

    @Autowired
    public void setQueryDispatcherManager(QueryDispatcherManager queryDispatcherManager) {
        this.queryDispatcherManager = queryDispatcherManager;
    }

    public HttpEntity<byte[]> query(byte[] request) throws IOException {
        HttpEntity<byte[]> httpEntity = queryDispatcherManager.dispatchHttpQuery(request);
        return httpEntity;
    }

    public CommonResult setAsBlackMod(){
        queryDispatcherManager.setMod_1();
        return CommonResult.success("设置成功");
    }
    public CommonResult setAsWhiteMod(){
        queryDispatcherManager.setMod_0();
        return CommonResult.success("设置成功");
    }

    public CommonResult changUpStreamUdp(String udpAddress, int udpPort){

        try {
            if (Inet4Address.getByName(udpAddress).getHostAddress().equals(udpAddress)) {
                queryDispatcherManager.setUdpAddress(udpAddress);
                queryDispatcherManager.setUdpPort(udpPort);
                logger.info("DNS UDP 服务器更改为：" + udpAddress);
            }
        } catch (UnknownHostException e) {
            return CommonResult.success("UDP 上游DNS服务地址更改失败！");
        }
        return CommonResult.success("UDP 上游DNS服务地址更改成功！");

    }

    public CommonResult changeUpStreamDoh(String urlString) {
        try {
            URL url = new URL(urlString);
            this.upstreamDoh.setDohUrl(url);
        } catch (MalformedURLException e) {
            logger.info("DOH修改失败:" + urlString);
            return CommonResult.failed("Url 格式错误！");
        }

        logger.info("修改上游DOH为:" + urlString);
        return CommonResult.success("修改成功");

    }




}
