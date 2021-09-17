package com.demo.gateway.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.netflix.zuul.context.RequestContext;
import com.demo.common.model.vo.Constants;
import com.demo.common.utils.JwtToken;
import com.demo.gateway.service.IUserAuthenService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserAuthenService implements IUserAuthenService {


    @Override
    public boolean doAuth(String url, RequestContext context) throws Exception {
        HttpServletRequest request = context.getRequest();
        if (url.indexOf("/noAuth/") > 0) {
            return true;
        } else if (url.indexOf("/auth/") > 0) {
            String token = request.getHeader("Authorization");
            boolean pass = JwtToken.validToken(token);
            if (pass) {
                Map<String, Claim> claim = JwtToken.verifyToken(token);
                String userId = claim.get(Constants.USERID).asString();
                String userAccount = claim.get(Constants.USERNAME).asString();// 用户账号
                String roleIds = claim.get(Constants.ROLEIDS).asString();

                context.addZuulRequestHeader("userId", userId);
                context.addZuulRequestHeader("userAccount", userAccount);
                context.addZuulRequestHeader("roleIds", roleIds);
                return true;
            }
            return false;
        }
        return false;
    }

    public static Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> headNames = request.getHeaderNames();
        while (headNames.hasMoreElements()) {
            String headName = headNames.nextElement();
            headers.put(headName, request.getHeader(headName));
        }
        return headers;
    }
}
