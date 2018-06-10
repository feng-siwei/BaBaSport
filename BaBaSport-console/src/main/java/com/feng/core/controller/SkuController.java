package com.feng.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feng.core.bean.product.Sku;
import com.feng.core.service.product.SkuService;

@Controller
@RequestMapping(value="/sku")
public class SkuController {
	@Autowired
	private SkuService skuService;
	
	//进入
	@RequestMapping(value="/list.do")
	public String list(Long productId,Model model) {
		List<Sku> skus = skuService.selectSkuListByProductId(productId);
		model.addAttribute("skus", skus);
		return "sku/list";
	}
//	/sku/addSku.do
	//异步更改
	@RequestMapping(value="addSku.do")
	public void addSku(Sku sku ,HttpServletResponse response) throws IOException {
		skuService.updateSkuById(sku);
		
		JSONObject jo = new JSONObject();
		jo.put("message", "恭喜,更改成功!!!");
		
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jo.toString());
	}
}
