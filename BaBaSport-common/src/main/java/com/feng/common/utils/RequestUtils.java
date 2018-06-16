package com.feng.common.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取CSESSIONID
 * @author 冯思伟
 *
 */
public class RequestUtils {
	//获取
	public static String getCSESSIONID(HttpServletRequest request,HttpServletResponse response) {
		//取出Coolie
		Cookie [] cookies = request.getCookies();
		if (null !=cookies&&cookies.length>0) {
			for (Cookie cookie : cookies) {
				//判读是否存在CSESSIONID
				if ("CSESSIONID".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		//新建
		String cSessionId = UUID.randomUUID().toString().replaceAll("-", "");
		Cookie cookie = new Cookie("CSESSIONID", cSessionId);
		//设置存活时间 -1 关闭浏览器销毁  0立即销毁   >0时间
		cookie.setMaxAge(-1);
		//设置路径
		cookie.setPath("/");
		//跨域 localhost == www.jd.com search.jd.com item.jd.com
		//cookie.setDomain(".jd.com");
		//写回
		response.addCookie(cookie);
		return cSessionId;
	}
}
