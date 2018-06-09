package com.feng.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feng.core.bean.product.Brand;
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
	//根据条件分页查询
	@RequestMapping(value="/list.do")
	public String list(String name ,Integer isDisplay,Integer pageNo,Model model){
		isDisplay = (isDisplay == null ) ? 1 : isDisplay;
		
		Pagination pagination = brandService.selectPaginationByquery(name, isDisplay, pageNo);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		
		
		model.addAttribute("isDisplay", isDisplay);
		return "brand/list";
	}
	//去修改页面
	@RequestMapping(value="/toEdit.do")
	public String toEdit(Long id,Model model){
		Brand brand =  brandService.selectBrandById(id);//快捷键 接收对象 shift+alt+L
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	
	//修改
	@RequestMapping(value="/edit.do")
	public String edit(Brand brand){
		brandService.updateBrandById(brand);
		return"redirect:/brand/list.do";
	}
}
