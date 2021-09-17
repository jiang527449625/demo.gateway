package com.demo.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.demo.common.mybatis.ResultGenerator;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseUrlFilter extends ZuulFilter {

    protected final String FILTER_ROUNT_KEY = "FILTER_ROUNT_KEY_STATUS";

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * filterType：返回过滤器的类型：pre、route、post、error
     */
    public String filterType() {
        return "pre";
    }


    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        context.getResponse().setCharacterEncoding("UTF-8");
        context.getResponse().setHeader("Cache-Control", "no-store");
        context.getResponse().setHeader("Content-type", "application/json;charset=UTF-8");
        // 如果 url认证失败，返回无效的URL
        if (null != context.get(this.FILTER_ROUNT_KEY) && !Boolean.valueOf((boolean) context.get(this.FILTER_ROUNT_KEY))) {
            //对该请求禁止路由，也就是禁止访问下游服务
            context.setSendZuulResponse(false);
            setErrorResp(JSON.toJSONString(ResultGenerator.genNoIntefaceResult("无效的URL")));
            return context;
        }
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("Authorization");
        context.addZuulRequestHeader("original_requestURL", request.getRequestURL().toString());
        context.addZuulRequestHeader("Authorization", token);
        Object filterResult = doFilter(context);
        return filterResult;
    }


    protected void setErrorResp(String message) {
        RequestContext context = RequestContext.getCurrentContext();
        //对该请求禁止路由，也就是禁止访问下游服务
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(200);
        //设定responseBody供PostFilter使用
        context.setResponseBody(message);
        //logic-is-success保存于上下文，作为同类型下游Filter的执行开关
        context.set(FILTER_ROUNT_KEY, false);
    }


    public abstract Object doFilter(RequestContext context);

}
