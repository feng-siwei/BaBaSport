package com.feng.core.service;


import cn.itcast.common.page.Pagination;

public interface SearchServuce {
	/**
	 * 返回结果集
	 * @param keyword
	 * @return
	 * @throws Exception 
	 */
	public Pagination selectPaginationByQuery(String keyword,Integer pageNo) throws Exception ;
}
