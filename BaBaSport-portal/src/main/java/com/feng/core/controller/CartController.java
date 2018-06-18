package com.feng.core.controller;

import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.common.utils.RequestUtils;
import com.feng.common.web.Constants;
import com.feng.core.bean.BuyerCart;
import com.feng.core.bean.BuyerItem;
import com.feng.core.bean.product.Sku;
import com.feng.core.service.product.SkuService;
import com.feng.core.service.user.SessionProvider;

@Controller
public class CartController {
	
	@Autowired
	private SkuService skuService;
	@Autowired
	private SessionProvider sessionProvider;
	
	//加入购物车
	@RequestMapping(value = "/addCart")
	public String addCart(Long skuId,Integer amount,Model model,
			HttpServletRequest request , HttpServletResponse response ) throws Exception {
		
		ObjectMapper om = new ObjectMapper();
		//忽略null值
		om.setSerializationInclusion(Include.NON_NULL);
		BuyerCart buyerCart = null;
		
		//1.取cookies
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0 ) {
			for (Cookie cookie : cookies) {
				//cookie中有无购物车
				if (Constants.BUYER_CART.equals(cookie.getName())) {
					//有
					buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
					break;
				}
			}
		}
		//如果没有购物车 就创建
		if (null == buyerCart) {
			buyerCart = new BuyerCart();
		}
		
		//追加当前商品到购物车
		Sku sku = new Sku();
		sku.setId(skuId);	
		BuyerItem buyerItem = new BuyerItem();
		buyerItem.setSku(sku);
		buyerItem.setAmount(amount);
		
		buyerCart.addItem(buyerItem);
		
		
		
		//用户是否登入
//		String username = sessionProvider.getAttribuerForUsername(RequestUtils.getCSESSIONID(request, response));
//		if (null != username) {
//			//登入用户将购物车储存到redis
//
//			//将当前购物车添加到redis中
//			skuService.insertBuyerCartToRedis(buyerCart, username);
//			//清理之前的coolies
//			Cookie cookie = new Cookie(Constants.BUYER_CART , null);
//			cookie.setMaxAge(0);
//			cookie.setPath("/");
//			response.addCookie(cookie);
//	
//		}else{
			//非登入用户将购物车储存到cookies
			
			//创建Cookie 放入购物车
			StringWriter sw = new StringWriter();
			om.writeValue(sw, buyerCart);
			Cookie cookie = new Cookie(Constants.BUYER_CART , sw.toString());
			//设置时间
			cookie.setMaxAge(60*60*24*1);
			//设置路径 同域共享
			cookie.setPath("/");
			//上线后设置域名 跨域使用
//			cookie.setDomain(".XXX.com");
			//保存写回浏览器
			response.addCookie(cookie);
//		}
		
		
		return "redirect:/toCart";   
	}
	
	
	
	//去购物车页面
	@RequestMapping(value = "/toCart")
	public String toCart(Integer amount,Model model,
			HttpServletRequest request , HttpServletResponse response ) throws Exception {
		ObjectMapper om = new ObjectMapper();
		//忽略null值
		om.setSerializationInclusion(Include.NON_NULL);
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
		//用户是否登入
		String username = sessionProvider.getAttribuerForUsername(RequestUtils.getCSESSIONID(request, response));
		if (null != username) {
			//登入
			if (null != buyerCart) {
				//将当前购物车添加到redis中,防止用户登入后直接在购物车页面进行刷新,导致cokie中的数据丢失
				skuService.insertBuyerCartToRedis(buyerCart, username);
				//清理之前的coolies
				Cookie cookie = new Cookie(Constants.BUYER_CART , null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);				
			}
			
			//从redis取出购物车
			buyerCart = skuService.selectBuyerCartFromRedis(username);
		}
		
		//数据回显
		//是否存在存在购物车
		if (null != buyerCart) {
			//存在,将SKU实例化 id→对象
			List<BuyerItem> items = buyerCart.getItems();
			for (BuyerItem buyerItem : items) {
				buyerItem.setSku(skuService.selectSkuByid(buyerItem.getSku().getId()));;
			}
		}
		//回显购物车内容
		model.addAttribute("buyerCart", buyerCart);
		
		return "cart";
	}
	
	//结算
	@RequestMapping(value = "/buyer/trueBuy")
	public String trueBuy(Long[] skuId,Model model, 
			HttpServletRequest request , HttpServletResponse response) throws Exception {
		//获得用户名
		String username = sessionProvider.getAttribuerForUsername(RequestUtils.getCSESSIONID(request, response));
		//从redis取出购物车
		BuyerCart buyerCart = skuService.selectBuyerCartFromRedis(username);
		List<BuyerItem> items = buyerCart.getItems();
		//缺货标识
		Boolean flag = false;
		if (items.size()>0) {
			//库存检查
			for (BuyerItem buyerItem : items) {
				buyerItem.setSku(skuService.selectSkuByid(buyerItem.getSku().getId()));
				if (buyerItem.getAmount()>buyerItem.getSku().getStock()) {
					//缺货
					buyerItem.setIsHave(false);
					flag = true;
				}
			}
			//缺货处理
			if (flag) {
				model.addAttribute("buyerCart", buyerCart);
				return "cart";
			}
		}else {
			//购物车为空
			return "redirect:/toCart";
		}

		return "order";
	}
}
