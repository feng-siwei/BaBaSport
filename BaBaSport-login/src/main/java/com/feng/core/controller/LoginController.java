package com.feng.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.druid.stat.TableStat.Mode;

/**
 * 单点登入系统
 * 
 * 页面转跳
 * 提交表单
 * 加密
 *  
 * @author 冯思伟
 *
 */
@Controller
public class LoginController {
	
	//去登入页面
	@RequestMapping(value="/login.aspx",method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	//提交登入
	@RequestMapping(value="/login.aspx",method = RequestMethod.POST)
	public String login(String username, String password,String returnUrl,Model model) {
		//用户名不能为空
		if (null != username) {
			if (null != password) {
				
			}else {
				model.addAttribute("error", "密码不能为空"); 
			}
		}else {
			model.addAttribute("error", "用户不能为空"); 
		}
		
		return "login";
	}
}
