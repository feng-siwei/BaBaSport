package com.feng.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feng.core.service.product.BrandService;

import cn.itcast.common.page.Pagination;

/**
 * 品牌管理 
 * 列表
 * 删除
 * 修改
 * 添加
 * 删除
 * @author 冯思伟
 *
 */
@Controller
@RequestMapping(value="/brand")
public class BrandController {
	
	@Autowired
 	private BrandService brandService ;
	//查询
	@RequestMapping(value="/list.do")
	public String list(String name ,Integer isDisplay,Integer pageNo,Model model){
		
		Pagination pagination = brandService.selectPaginationByquery(name, isDisplay, pageNo);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		
		isDisplay = (isDisplay == null ) ? 1 : isDisplay;
		model.addAttribute("isDisplay", isDisplay);
		return "brand/list";
	}
}
