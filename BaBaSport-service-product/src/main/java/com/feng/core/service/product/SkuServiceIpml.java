package com.feng.core.service.product;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feng.common.web.Constants;
import com.feng.core.bean.BuyerCart;
import com.feng.core.bean.BuyerItem;
import com.feng.core.bean.product.Color;
import com.feng.core.bean.product.Sku;
import com.feng.core.bean.product.SkuQuery;
import com.feng.core.dao.product.ColorDao;
import com.feng.core.dao.product.ProductDao;
import com.feng.core.dao.product.SkuDao;

import redis.clients.jedis.Jedis;

@Service("skuService")
@Transactional
public class SkuServiceIpml implements SkuService {
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	@Autowired
	private ProductDao ProductDao;
	@Autowired
	private Jedis jedis;
	
	//查询库存
	@Override
	public List<Sku> selectSkuListByProductId(Long produtId) {
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(produtId);
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		for (Sku sku : skus) {
			Color color = colorDao.selectByPrimaryKey(sku.getColorId());
			sku.setColor(color);
		}
		return skus;
	}
	
	//更改库存
	public void updateSkuById(Sku sku) {
		skuDao.updateByPrimaryKeySelective(sku);
	}
	
	//通过ID查询SKU对象
	@Override
	public Sku selectSkuByid(Long id) {
		Sku sku = skuDao.selectByPrimaryKey(id);
		//商品对象
		sku.setProduct(ProductDao.selectByPrimaryKey(sku.getProductId()));
		//颜色对象
		sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		return sku;
	}
	
	//购物车储存到redis中
	@Override
	public void insertBuyerCartToRedis(BuyerCart buyerCart,String username){
		List<BuyerItem> items = buyerCart.getItems();
		if (items.size()>0) {
			for (BuyerItem buyerItem : items) {
				//判断是否存在
//				if (jedis.hexists(Constants.BUYER_CART+":"+username, 
//						String.valueOf(buyerItem.getSku().getId()))) {
					//存在追加
					jedis.hincrBy(Constants.BUYER_CART+":"+username, 
							String.valueOf(buyerItem.getSku().getId()), 
							buyerItem.getAmount());
//				}else {
//					jedis.hset(Constants.BUYER_CART+":"+username, 
//							String.valueOf(buyerItem.getSku().getId()),
//							String.valueOf(buyerItem.getAmount()));					
//				}
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
