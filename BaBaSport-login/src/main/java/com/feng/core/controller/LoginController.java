package com.feng.core.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feng.common.utils.RequestUtils;
import com.feng.core.bean.user.Buyer;
import com.feng.core.service.user.BuyerService;
import com.feng.core.service.user.SessionProvider;

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
	
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private SessionProvider sessionProvider;
	//去登入页面
	@RequestMapping(value="/login.aspx",method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	//判读是否登入 ajax
	@RequestMapping(value="/isLogin.aspx")
	public @ResponseBody MappingJacksonValue isLogin(String callback,HttpServletRequest request ,HttpServletResponse response) {
		Integer result = 0;
		String username = sessionProvider.getAttribuerForUsername(RequestUtils.getCSESSIONID(request, response));
		if(null != username){
			result = 1;
		}
		MappingJacksonValue mjv = new MappingJacksonValue(result);
		
		mjv.setJsonpFunction(callback);
		
		return mjv;
	}
	
	//提交登入
	@RequestMapping(value="/login.aspx",method = RequestMethod.POST)
	public String login(String username, String password,String returnUrl,
			HttpServletRequest request ,HttpServletResponse response,Model model) {
		//用户名不能为空
		if (null != username) {
			//密码不能为空
			if (null != password) {
				//用户名
				Buyer buyer = buyerService.selectBuyerByUsername(username);
				if (null != buyer) {
					//密码
					if (buyer.getPassword().equals(encodePassword(password))) {
						//保存用户名到session(Redis)
						sessionProvider.setAttribuerForUsername(RequestUtils.getCSESSIONID(request, response), buyer.getUsername());
						//返回之前页面
						return "redirect:"+returnUrl;
						
					}else {
						model.addAttribute("error", "密码错误"); 
					}
				}else {
					model.addAttribute("error", "用户名不存在"); 
				}
			}else {
				model.addAttribute("error", "密码不能为空"); 
			}
		}else {
			model.addAttribute("error", "用户不能为空"); 
		}
		
		return "login";
	}
	
	//加密  （密码过于简单）有规则密码 
	public String encodePassword(String password){
		//
		//password = "gxzcwefxcbergfd123456errttyyytytrnfzeczxcvertwqqcxvxb";
		//1:MD5  算法
		String algorithm = "MD5";
		char[] encodeHex = null;
		try {
			//MD5加密
			MessageDigest instance = MessageDigest.getInstance(algorithm);
			//加密后  
			byte[] digest = instance.digest(password.getBytes());
			//
			//2:十六进制
			encodeHex = Hex.encodeHex(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(encodeHex);
	}
	
}
