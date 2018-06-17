package com.feng.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * @author 冯思伟
 *
 */
public class BuyerCart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//购物商品集合
	private List<BuyerItem> items = new ArrayList<>();
	/**
	 * 添加购物项到购物车中
	 * @param item
	 */
	
	//小计
	
	
	
	
	public void addItem(BuyerItem item){
		items.add(item);
	}
	public List<BuyerItem> getItems() {
		return items;
	}
	public void setItems(List<BuyerItem> items) {
		this.items = items;
	}
}
