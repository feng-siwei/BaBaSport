package com.feng.core.service;

import java.util.List;

import com.feng.core.bean.product.Product;
import com.feng.core.bean.product.Sku;

public interface CmsService {
	/**
	 * 查询商品
	 * @param id
	 * @return
	 */
	public Product selectProductById(Long id);
	
	/**
	 * 查询库存结果集
	 * @param productId
	 * @return
	 */
	public List<Sku> selectSkuListByProductId(Long productId);
}
