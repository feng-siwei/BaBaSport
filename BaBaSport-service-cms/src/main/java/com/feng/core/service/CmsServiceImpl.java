package com.feng.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feng.core.bean.product.Product;
import com.feng.core.bean.product.Sku;
import com.feng.core.bean.product.SkuQuery;
import com.feng.core.dao.product.ColorDao;
import com.feng.core.dao.product.ProductDao;
import com.feng.core.dao.product.SkuDao;

/**
 * 评论
 * 晒单
 * 广告
 * 静态化
 * @author 冯思伟
 *
 */
@Service("cmsService")
public class CmsServiceImpl implements CmsService{

	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	
	//查询商品
	@Override
	public Product selectProductById(Long id) {
		return productDao.selectByPrimaryKey(id);
	}

	//查询有货库存
	@Override
	public List<Sku> selectSkuListByProductId(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		//商品ID与库存大于0
		skuQuery.createCriteria().andProductIdEqualTo(productId).andStockGreaterThan(0);
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		for (Sku sku : skus) {
			sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		}
		return skus;
	}

}
