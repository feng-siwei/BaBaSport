package com.feng.core.controller;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.common.web.Constants;
import com.feng.core.bean.BuyerCart;
import com.feng.core.bean.BuyerItem;
import com.feng.core.bean.product.Sku;

public class CartController {
	//加入购物车
	@RequestMapping(value = "/toCart")
	public String toCart(Long skuId,Integer amount,Model model,
			HttpServletRequest request , HttpServletResponse response ) throws Exception {
		
		ObjectMapper om = new ObjectMapper(); 
		BuyerCart buyerCart = null;
		
		//1.取cookies
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0 ) {
			for (Cookie cookie : cookies) {
				//cookie中有无购物车
				if (Constants.BUYER_CART.equals(cookie.getName())) {
					buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
					break;
				}
			}
		}
		//如果没有购物车 就创建
		if (buyerCart == null) {
			buyerCart = new BuyerCart();
		}
		
		//追加当前商品到购物车
		Sku sku = new Sku();
		sku.setId(skuId);
		
		BuyerItem buyerItem = new BuyerItem();
		buyerItem.setSku(sku);
		buyerItem.setAmount(amount);
		
		buyerCart.addItem(buyerItem);
		
		//创建Cookie 放入购物车
		StringWriter sw = new StringWriter();
		om.writeValue(sw, buyerCart);
		Cookie cookie = new Cookie(Constants.BUYER_CART , sw.toString());
		//设置时间
		cookie.setMaxAge(60*60*24*1);
		//设置路径
		cookie.setPath("/");
		//上线域名
		
		//保存写回浏览器
		response.addCookie(cookie);
		
		
		
		
		
		return "";   
	}
}
