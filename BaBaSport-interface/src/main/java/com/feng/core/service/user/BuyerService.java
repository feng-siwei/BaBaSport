package com.feng.core.service.user;

import com.feng.core.bean.user.Buyer;

public interface BuyerService {
	
	/**
	 * 通过用户名查询用户对象
	 * @param username 用户名
	 * @return
	 */
	public Buyer selectBuyerByUsername(String username);

}
