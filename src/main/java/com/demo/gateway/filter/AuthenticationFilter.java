package com.demo.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
import com.demo.common.mybatis.ResultGenerator;
import com.demo.gateway.service.IGateWayService;
import com.demo.gateway.service.IUserAuthenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationFilter extends UrlFilter {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private IGateWayService gateWayService;
    @Autowired
    private IUserAuthenService userAuthenService;


    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public Object doFilter(RequestContext context) {
        String url = context.getRequest().getRequestURI();
        logger.info("验证需要进行权限认证的接口，{}", url);
        try {
            if (gateWayService.isAuth(url) && !userAuthenService.doAuth(url, context)) {
                //对该请求禁止路由，也就是禁止访问下游服务
                context.setSendZuulResponse(false);
                super.setErrorResp(JSON.toJSONString(ResultGenerator.genUnauthorizedResult("登陆认证失败!")));
                return context;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("验证需要进行权限认证的接口异常，{}", e);
        }
        return url;
    }


}
