package com.feng.core.bean;

import com.feng.core.bean.product.Sku;

/**
 * 购物项
 * @author 冯思伟
 *
 */
public class BuyerItem {
	private Sku sku;
	//是否有货
	private Boolean isHave = true;
	//数量
	private Integer amount = 1;
	
	
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public Boolean getIsHave() {
		return isHave;
	}
	public void setIsHave(Boolean isHave) {
		this.isHave = isHave;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "BuyerItem [sku=" + sku + ", isHave=" + isHave + ", amount=" + amount + "]";
	}
	
	
	
}
