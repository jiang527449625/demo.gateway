package com.demo.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
import com.demo.common.mybatis.ResultGenerator;
import com.demo.gateway.service.IGateWayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UrlFilter extends BaseUrlFilter {

    private static Logger logger = LoggerFactory.getLogger(UrlFilter.class);

    @Autowired
    IGateWayService gateWayService;


    @Override
    public int filterOrder() {
        return 0;
    }


    @Override
    public Object doFilter(RequestContext context) {
        String url = context.getRequest().getRequestURI();
        logger.info("验证访问的接口:" + url);
        if (!gateWayService.doHandler(url)) {
            //对该请求禁止路由，也就是禁止访问下游服务
            context.setSendZuulResponse(false);
            //到这里此Filter逻辑结束
            setErrorResp(JSON.toJSONString(ResultGenerator.genNoIntefaceResult("接口不在允许的范围")));
            return context;
        }
        return url;
    }


}
