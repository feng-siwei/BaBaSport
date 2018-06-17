package com.feng.core.service.product;

import java.util.List;

import com.feng.core.bean.BuyerCart;
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
	
	/**
	 * 添加购物车到redis数据库中
	 * @param username 用户名
	 * @param buyerCart
	 */
	public void insertBuyerCartToRedis(BuyerCart buyerCart,String username);
	
	/**
	 * 从redis根据用户名中取出购物车
	 * @param username 用户名
	 * @return
	 */
	public BuyerCart selectBuyerCartFromRedis(String username);
	
	
}
