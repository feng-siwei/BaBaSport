package com.feng.core.interceptor;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.feng.common.utils.RequestUtils;
import com.feng.core.service.user.SessionProvider;

/**
 * 拦截器
 * @author 冯思伟
 *
 */
public class CustomInterceptor implements HandlerInterceptor{

	@Autowired
	private SessionProvider sessionProvider;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//需要登入 
		String username = sessionProvider.getAttribuerForUsername(RequestUtils.getCSESSIONID(request, response));
		if (null != username) {
			return true;
		}
		response.sendRedirect("http://localhost:8081/login.aspx?returnUrl=http://localhost:8082/index.do");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
