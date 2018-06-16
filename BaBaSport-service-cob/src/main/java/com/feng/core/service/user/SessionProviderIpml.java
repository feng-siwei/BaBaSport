package com.feng.core.service.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.feng.common.web.Constants;

import redis.clients.jedis.Jedis;

public class SessionProviderIpml implements SessionProvider {
	@Autowired
	private Jedis jedis;
	
	//过期时间 单位分钟  默认30分钟
	private Integer exp =30 ;
	/**
	 * 设置过期时间 单位分钟
	 * @param exp
	 */
	public void setExp(Integer exp) {
		this.exp = exp;
	}

	//保存到redis
	@Override
	public void setAttribuerForUsername(String name, String value) {
		jedis.set(name + ":" + Constants.USER_NAME, value);
		//时间
		jedis.expire(name + ":" + Constants.USER_NAME, exp*60);
	}

	@Override
	public String getAttribuerForUsername(String name) {
		String value = jedis.get(name + ":" + Constants.USER_NAME);
		//刷新时间
		if (value != null) {
			jedis.expire(name + ":" + Constants.USER_NAME, exp*60);
		}
		return value;
	}
	
	
}
