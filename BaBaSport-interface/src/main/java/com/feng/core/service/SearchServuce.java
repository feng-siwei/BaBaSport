package com.feng.core.service;

import java.util.List;

import com.feng.core.bean.product.Product;

public interface SearchServuce {
	/**
	 * 返回结果集
	 * @param keyword
	 * @return
	 * @throws Exception 
	 */
	public List<Product> selectProducListByQuery(String keyword) throws Exception ;
}
