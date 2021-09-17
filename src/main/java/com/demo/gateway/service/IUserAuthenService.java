package com.demo.gateway.service;

import com.netflix.zuul.context.RequestContext;

public interface IUserAuthenService {

    /**
     * 用户配置的权限信息，进行权限认证
     *
     * @param url
     * @return
     */
    boolean doAuth(String url, RequestContext context) throws Exception;
}
