package pers.diego.dns.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.diego.dns.bo.CommonResult;
import pers.diego.dns.component.UpstreamDoh;
import pers.diego.dns.mangager.QueryDispatcherManager;
import pers.diego.dns.util.DomainTreeUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author kang.zhang
 * @date 5/15/2022 12:24 PM
 */
@Service
public class DomainService {

    private Logger logger = LoggerFactory.getLogger(DomainService.class.getName());

    private UpstreamDoh upstreamDoh;

    @Autowired
    public void setUpstreamDoh(UpstreamDoh upstreamDoh) {
        this.upstreamDoh = upstreamDoh;
    }

    public CommonResult addDomain(String domain){
        boolean addDomain = DomainTreeUtil.addDomain(domain);
        if (addDomain){
            return CommonResult.success("添加成功");
        }else {
            return CommonResult.failed("添加失败，稍后再试试！");
        }
    }

    public CommonResult delDomain(String domain){
        boolean addDomain = DomainTreeUtil.deleteDomain(domain);
        if (addDomain){
            return CommonResult.success("添加成功");
        }else {
            return CommonResult.failed("添加失败，稍后再试试！");
        }
    }

    public CommonResult changeUpStreamDoh(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            logger.info("DOH修改失败:" + url);
            return CommonResult.failed("Url 格式错误！");

        }
        this.upstreamDoh.setHttpUrl(url);
        logger.info("修改上游DOH为:" + url);
        return CommonResult.success("修改成功");


    }


}
