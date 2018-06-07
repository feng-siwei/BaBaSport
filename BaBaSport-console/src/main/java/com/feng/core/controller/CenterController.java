package com.feng.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台管理
 * @author 冯思伟
 *
 */
@Controller
public class CenterController {
	/**
	 * modelAndView 跳转视图+数据 不用
	 * void 异步ajax使用
	 * string 跳转视图 + model
	 */
	@RequestMapping(value = "/test/index.do")
	public String index(){
		
		return "index";
	}
}
