package com.feng.core.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 后台管理
 * @author 冯思伟
 *
 */
@Controller
@RequestMapping(value = "/center")
public class CenterController {
	
	//首页
	/**
	 * modelAndView 跳转视图+数据 不用
	 * void 异步ajax使用
	 * string 跳转视图 + model
	 */
	@RequestMapping(value = "/index.do")
	public String index(Model model) {
		
		return "index";
	}
	
	//头
	@RequestMapping(value = "/top.do")
	public String top(Model model) {

		return "top";
	}
	//身体
	@RequestMapping(value = "/main.do")
	public String main(Model model) {

		return "main";
	}
	//身体_左
	@RequestMapping(value = "/left.do")
	public String left(Model model) {
		
		return "left";
	}
	//身体_左
		@RequestMapping(value = "/right.do")
		public String right(Model model) {
			
			return "right";
	}
	//商品身体
	@RequestMapping(value = "/frame/product_main.do")
	public String product_main(Model model) {
		
		return "frame/product_main";
	}
	//商品左
	@RequestMapping(value = "/frame/product_left.do")
	public String product_left(Model model) {
		
		return "frame/product_left";
	}

	//brand/list.do



}
