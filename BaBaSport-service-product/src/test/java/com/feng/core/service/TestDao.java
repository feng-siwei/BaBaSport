package com.feng.core.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.feng.core.bean.product.Brand;
import com.feng.core.bean.product.BrandQuery;
import com.feng.core.dao.product.BrandDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class TestDao {
	@Autowired
	private BrandDao brandDao;
	
	@Test
	public void testBrandDao()throws Exception {
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setIsDisplay(1);
		Integer count = brandDao.selectCount(brandQuery);
		System.out.println(count);
		List<Brand> brands = brandDao.selectBrandListByQuery(brandQuery);
		System.out.println(brands);
		
		
	}
}
