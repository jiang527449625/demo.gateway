package com.demo.gateway.service.impl;

import com.demo.gateway.service.IGateWayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class GateWayService implements IGateWayService, CommandLineRunner {
	
	private static Logger logger = LoggerFactory.getLogger(GateWayService.class);
	
	private String[] rountUrls = null;
	
	private String[] authUrls = null;
	
	@Value("${rounts.security.gateway}")
	private boolean gateway = false;
	
	@Value("${rounts.security.url}")
	private String rountUrlInfo = "";
	
	@Value("${rounts.security.auth.gateway}")
	private boolean authGateway = false;
	
	
	@Override
	public boolean doHandler(String url) {
		// 开关关闭时，允许访问所有的配置信息。
		if (!gateway)
			return true;
		// 进行url的匹配。
		for (String s : rountUrls) {
			if(url.endsWith(s))
				return true;
		}
		logger.warn("【"+url +"】 不在允许的范围");
		return false;
	}

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("路由的服务接口 "+rountUrlInfo);
		rountUrls = rountUrlInfo.split(",");
	}

	@Override
	public boolean isAuth(String url) {
		// 开关
		return authGateway;
	}

	public String[] getRountUrls() {
		return rountUrls;
	}

	public void setRountUrls(String[] rountUrls) {
		this.rountUrls = rountUrls;
	}

	public String[] getAuthUrls() {
		return authUrls;
	}

	public void setAuthUrls(String[] authUrls) {
		this.authUrls = authUrls;
	}

	public boolean isGateway() {
		return gateway;
	}

	public void setGateway(boolean gateway) {
		this.gateway = gateway;
	}

	public String getRountUrlInfo() {
		return rountUrlInfo;
	}

	public void setRountUrlInfo(String rountUrlInfo) {
		this.rountUrlInfo = rountUrlInfo;
	}

	public boolean isAuthGateway() {
		return authGateway;
	}

	public void setAuthGateway(boolean authGateway) {
		this.authGateway = authGateway;
	}
}
