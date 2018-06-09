package com.feng.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feng.core.bean.product.Brand;
import com.feng.core.service.product.BrandService;
import com.feng.core.service.product.ProductService;

import cn.itcast.common.page.Pagination;

@Controller
@RequestMapping(value="/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	
	
	@RequestMapping(value="/list.do")
	public String list(Integer pageNo, String name, Long brandId, Boolean isShow,Model model){
		
		isShow = (isShow == null ) ? false : isShow;
		Pagination pagination = productService.selectPaginationByQuery(pageNo, name, brandId, isShow);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
		
		//品牌下拉框
		List<Brand> brands = brandService.selectDisplayBrand();
		model.addAttribute("brands", brands);
	
		return "product/list";
	}
	
}
