package com.feng.core.service;

import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.core.bean.user.Buyer;

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
