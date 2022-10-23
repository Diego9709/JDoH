package pers.diego.dns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import pers.diego.dns.bo.CommonResult;
import pers.diego.dns.mangager.QueryDispatcherManager;

import java.io.IOException;

/**
 * @author kang.zhang
 * @date 2021/11/25 14:50
 */

@Service
public class DnsQueryService {

    public QueryDispatcherManager queryDispatcherManager;


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
}
