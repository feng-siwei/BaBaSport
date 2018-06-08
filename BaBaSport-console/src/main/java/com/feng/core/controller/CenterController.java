package com.feng.core.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feng.core.bean.Test;
import com.feng.core.service.TestService;

/**
 * 后台管理
 * @author 冯思伟
 *
 */
@Controller
public class CenterController {
	
	@Autowired
	private TestService testService;
	
	//入口
	/**
	 * modelAndView 跳转视图+数据 不用
	 * void 异步ajax使用
	 * string 跳转视图 + model
	 */
	@RequestMapping(value = "/test/index.do")

	
	public String index(Model model) {
		Test test = new Test();
		test.setName("Dubbo测试数据");
//		test.setBirthday(new Date(System.currentTimeMillis()));
		testService.insertTest(test);
		
		return "index";
	}
	
}
