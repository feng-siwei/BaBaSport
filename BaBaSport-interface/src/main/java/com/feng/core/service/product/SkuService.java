package com.feng.core.service.product;

import java.util.List;

import com.feng.core.bean.product.Sku;

public interface SkuService {
	/**
	 * 库存查询
	 * @param produtId
	 * @return
	 */
	public List<Sku> selectSkuListByProductId(Long productId) ;
	
	/**
	 * 库存更改
	 * @param produtId
	 * @return
	 */
	public void updateSkuById(Sku sku) ;
	
	
	
	
}
