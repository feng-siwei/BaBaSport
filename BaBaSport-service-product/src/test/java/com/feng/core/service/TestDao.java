package com.feng.core.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.feng.core.bean.product.Brand;
import com.feng.core.bean.product.BrandQuery;
import com.feng.core.bean.product.Product;
import com.feng.core.bean.product.ProductQuery;
import com.feng.core.dao.product.BrandDao;
import com.feng.core.dao.product.ProductDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class TestDao {
	@Autowired
	private BrandDao brandDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testBrandDao()throws Exception {
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.createCriteria().andNameLike("%乐%");
		List<Brand> brands = brandDao.selectByExample(brandQuery);
		System.out.println(brands);
		
	}
	

	@Test
	public void testProductDaoDao()throws Exception {
		ProductQuery productQuery = new ProductQuery();
		
		//分页
		productQuery.setPageNo(2);
		productQuery.setPageSize(10);
		//排序
		productQuery.setOrderByClause("id desc");
		//指定字段查询
		productQuery.setFields("id,brand_id,name,weight");
		List<Product> products = productDao.selectByExample(productQuery);
		for (Product product : products) {
			System.out.println(product);
		}
		
	}
}
