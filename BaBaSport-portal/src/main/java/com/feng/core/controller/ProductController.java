package com.feng.core.controller;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feng.core.bean.product.Brand;
import com.feng.core.bean.product.Color;
import com.feng.core.bean.product.Product;
import com.feng.core.bean.product.Sku;
import com.feng.core.service.CmsService;
import com.feng.core.service.SearchServuce;
import com.feng.core.service.product.BrandService;

import cn.itcast.common.page.Pagination;

@Controller
public class ProductController {
	@Autowired
	private SearchServuce searchServuce;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CmsService cmsService;

	//首页
	@RequestMapping(value = "/index.do")
	public String index(Model model) {

		return "index";
	}
	
	//搜索
	@RequestMapping(value = "/search")
	public String index(String keyword ,Integer pageNo,Long brandId,String price,Model model) throws Exception {
		//solr搜索查询
		Pagination pagination  = searchServuce.selectPaginationByQuery(keyword,pageNo,brandId,price);
		model.addAttribute("pagination", pagination);
		
		//redis品牌合集查询
		List<Brand> brands = brandService.selectBrandListFromRedis();
		model.addAttribute("brands", brands);
		
		//回显条件
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		model.addAttribute("keyword", keyword);
		
		//已选条件容器 Map 回显上方选择条
		Map<String, String>map = new LinkedHashMap<>();
		//品牌
		if (null != brandId) {
			for (Brand brand : brands) {
				if(brandId == brand.getId()){
					map.put("品牌", brand.getName());
					break;
				}
			}
		}
		//价格
		if (null != price) {
			if (price.contains("-")) {
				map.put("价格", price);
			}
			else {
				map.put("价格", price+"以上");
			} 
		}
		model.addAttribute("map", map);
		
		return "search";
	}

	//去商品详情页面
	@RequestMapping(value="/product/detail")
	public String detail(Long id , Model model){
		//商品
		Product product = cmsService.selectProductById(id);
		//sku
		List<Sku> skus = cmsService.selectSkuListByProductId(id);
		//颜色集合
		Set<Color> colors = new HashSet<>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		
		model.addAttribute("product", product);
		model.addAttribute("skus", skus);
		model.addAttribute("colors", colors);
		return "product";
	}
}
