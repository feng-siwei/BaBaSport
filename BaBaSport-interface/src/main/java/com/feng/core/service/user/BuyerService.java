package com.feng.core.service.user;

import com.feng.core.bean.BuyerCart;
import com.feng.core.bean.order.Order;
import com.feng.core.bean.user.Buyer;

public interface BuyerService {
	
	/**
	 * 通过用户名查询用户对象
	 * @param username 用户名
	 * @return
	 */
	public Buyer selectBuyerByUsername(String username);

	/**
	 * 保存订单
	 * @param order 订单
	 * @param username 用户名
	 */
	public void insertOrder(Order order, String username);
	
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
