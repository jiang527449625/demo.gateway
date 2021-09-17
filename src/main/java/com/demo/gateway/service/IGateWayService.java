package com.demo.gateway.service;

/**
 *
 * @author jky
 *
 */
public interface IGateWayService {
	/**
	 * 判断用户访问的Url,是否在允许的范围中。
	 * @param url
	 * @return true,说明允许访问，false：不允许访问
	 */
	public boolean doHandler(String url);
	
	
	
	/**
	 * 判断用户访问的Url,是否在允许的范围中。
	 * @param url
	 * @return true,说明允许访问，false：不允许访问
	 */
	public boolean isAuth(String url);
}
