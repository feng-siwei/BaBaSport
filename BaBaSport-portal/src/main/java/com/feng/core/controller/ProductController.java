package com.feng.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
	
	//首页
	@RequestMapping(value = "/index.do")
	public String index(Model model) {

		return "index";
	}
}
