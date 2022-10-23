package pers.diego.dns.service;

import org.springframework.stereotype.Service;
import pers.diego.dns.bo.CommonResult;
import pers.diego.dns.util.DomainTreeUtil;

/**
 * @author kang.zhang
 * @date 5/15/2022 12:24 PM
 */
@Service
public class DomainService {
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

}
