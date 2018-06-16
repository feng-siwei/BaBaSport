package com.feng.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * @author 冯思伟
 *
 */
public class BuyerCart {
	//购物商品集合
	private List<BuyerItem> items = new ArrayList<>();
	/**
	 * 添加购物项到购物车中
	 * @param item
	 */
	public void addItem(BuyerItem item){
		items.add(item);
	}
	
	//小计
	
}
