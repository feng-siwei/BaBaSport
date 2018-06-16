package com.feng.core.service;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.core.bean.product.Brand;
import com.feng.core.bean.product.BrandQuery;
import com.feng.core.bean.product.Product;
import com.feng.core.bean.product.ProductQuery;
import com.feng.core.bean.user.Buyer;
import com.feng.core.dao.product.BrandDao;
import com.feng.core.dao.product.ProductDao;

public class TestJson {
	
	@Test
	public void testJSON()throws Exception {
		
		ObjectMapper om = new ObjectMapper();
		Buyer buyer = new Buyer();
		buyer.setUsername("范德萨");
		
		//忽略null值
		om.setSerializationInclusion(Include.NON_NULL);
		
		Writer w = new StringWriter();
		om.writeValue(w, buyer);
		System.out.println(w.toString());
		
		System.out.println("1111111111111");
		//转回
		Buyer r = om.readValue(w.toString(), buyer.getClass());
		System.out.println(r);
		
	}
	

}
