package com.feng.core.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.feng.core.bean.user.Buyer;
import com.feng.core.bean.user.BuyerQuery;
import com.feng.core.dao.user.BuyerDao;

public class BuyerServiceImpl implements BuyerService {
	
	@Autowired
	private BuyerDao buyerDao;
	
	@Override
	//通过用户名查询用户对象
	public Buyer selectBuyerByUsername(String username) {
		BuyerQuery buyerQuery = new BuyerQuery();
		buyerQuery.createCriteria().andUsernameEqualTo(username);
		
		List<Buyer> buyers = buyerDao.selectByExample(buyerQuery);
		if (null != buyers && buyers.size()>0) {
			return buyers.get(0);
		}
		return null;
				
	}
}
