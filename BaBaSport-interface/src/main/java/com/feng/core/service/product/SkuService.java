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

	/**
	 * 通过id查询Sku对象
	 * 包括颜色集合和商品集合
	 * @param id
	 * @return
	 */
	public Sku selectSkuByid(Long id);
	
	

	
}
