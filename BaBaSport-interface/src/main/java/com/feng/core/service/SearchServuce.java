package com.feng.core.service;


import cn.itcast.common.page.Pagination;

public interface SearchServuce {
	/**
	 * 搜索返回结果集
	 * @param keyword 搜索关键字
	 * @param pageNo 当前页数
	 * @param brandId 品牌条件id
	 * @param price 价格条件
	 * @return	分页集合
	 * @throws Exception 
	 */
	public Pagination selectPaginationByQuery(String keyword,Integer pageNo, Long brandId, String price) throws Exception ;
}
