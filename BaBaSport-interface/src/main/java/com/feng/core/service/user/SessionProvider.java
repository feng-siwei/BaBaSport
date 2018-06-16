package com.feng.core.service.user;

public interface SessionProvider {
	/**
	 * 根据用户名字储存redis数据
	 * @param name
	 * @param value
	 */
	public void setAttribuerForUsername(String name,String value) ;
	
	/**
	 * 根据用户名字获得redis数据
	 * @param name
	 * @return
	 */
	public String getAttribuerForUsername(String name) ;
}
