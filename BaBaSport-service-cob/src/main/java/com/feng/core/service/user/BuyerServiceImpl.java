package com.feng.core.service.user;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feng.common.web.Constants;
import com.feng.core.bean.BuyerCart;
import com.feng.core.bean.BuyerItem;
import com.feng.core.bean.order.Order;
import com.feng.core.bean.product.Sku;
import com.feng.core.bean.user.Buyer;
import com.feng.core.bean.user.BuyerQuery;
import com.feng.core.dao.user.BuyerDao;

import redis.clients.jedis.Jedis;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService {
	
	@Autowired
	private BuyerDao buyerDao;
	@Autowired
	private Jedis jedis;
	
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
	
	//保存订单
	@Override
	public void insertOrder(Order order ,String username){
		Long id = jedis.incr("oid");
		order.setId(id);
		//加载购物车
		BuyerCart buyerCart = selectBuyerCartFromRedis(username);
	}
	
	//购物车储存到redis中
		@Override
		public void insertBuyerCartToRedis(BuyerCart buyerCart,String username){
			List<BuyerItem> items = buyerCart.getItems();
			if (items.size()>0) {
				for (BuyerItem buyerItem : items) {
					//判断是否存在
//					if (jedis.hexists(Constants.BUYER_CART+":"+username, 
//							String.valueOf(buyerItem.getSku().getId()))) {
						//存在追加
						jedis.hincrBy(Constants.BUYER_CART+":"+username, 
								String.valueOf(buyerItem.getSku().getId()), 
								buyerItem.getAmount());
//					}else {
//						jedis.hset(Constants.BUYER_CART+":"+username, 
//								String.valueOf(buyerItem.getSku().getId()),
//								String.valueOf(buyerItem.getAmount()));					
//					}
				}
			}
		}
	
	//根据用户名到redis查找购物车
	@Override
	public BuyerCart selectBuyerCartFromRedis(String username) {
		BuyerCart buyerCart = new BuyerCart();

		Map<String, String> hgetAll = jedis.hgetAll(Constants.BUYER_CART+":"+username);
		if (null != hgetAll) {
			Set<Entry<String, String>> entrySet = hgetAll.entrySet();
			for (Entry<String, String> entry : entrySet) {
				//追加当前商品到购物车
				Sku sku = new Sku();
				sku.setId(Long.parseLong(entry.getKey()));	
				BuyerItem buyerItem = new BuyerItem();
				buyerItem.setSku(sku);
				buyerItem.setAmount(Integer.parseInt(entry.getValue()));

				buyerCart.addItem(buyerItem);
			}
		}
		return buyerCart;
	}
}
