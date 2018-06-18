package com.feng.core.bean;

import java.io.Serializable;

import com.feng.core.bean.product.Sku;

/**
 * 购物项
 * @author 冯思伟
 *
 */
public class BuyerItem implements Serializable{
	private static final long serialVersionUID = 1L;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)  //比较地址
			return true;
		if (obj == null) //非空判断
			return false;
		if (getClass() != obj.getClass()) //类比较 class 比较全路径
			return false;
		BuyerItem other = (BuyerItem) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.getId().equals(other.sku.getId()))
			return false;
		return true;
	}
	
	
	
}
