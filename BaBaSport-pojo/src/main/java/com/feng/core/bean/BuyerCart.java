package com.feng.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	public void addItem(BuyerItem item){
		//判断同款
		int index = items.indexOf(item);
		if( index > -1){
			 items.get(index).setAmount( items.get(index).getAmount()+item.getAmount());
		}else{
			items.add(item);
		}
	}
	public List<BuyerItem> getItems() {
		return items;
	}
	public void setItems(List<BuyerItem> items) {
		this.items = items;
	}
	
//	商品小计 注意在json转换时忽略以下内容,否则会因为不是标准javabean导致转换报错
	/**
	 * 商品数量
	 * @return
	 */
	@JsonIgnore
	public Integer getProductAmount() {
		Integer result = 0;
		for (BuyerItem buyerItem : items) {
			result += buyerItem.getAmount();
		}
		return result;
	}
	/**
	 * 商品金额
	 * @return
	 */
	@JsonIgnore
	public Float getProductPrice() {
		Float result = 0f;
		for (BuyerItem buyerItem : items) {
			result += buyerItem.getAmount()*buyerItem.getSku().getPrice();
		}		
		return result;
	}
	/**
	 * 运费
	 */
	@JsonIgnore
	public Float getFee() {
		Float result = 0f;
		if (getProductPrice() < 79) {
			result = 5f;
		}		
		return result;
	}
	
	/**
	 * 总金额
	 */
	@JsonIgnore
	public Float getTotalPrice() {
		return getProductPrice()+getFee();
	}
}
