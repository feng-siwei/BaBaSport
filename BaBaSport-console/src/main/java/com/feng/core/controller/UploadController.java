package com.feng.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.feng.common.web.Constants;
import com.feng.core.service.product.UploadService;

/**
 * 上传图片
 * @author 冯思伟
 *
 */
@Controller
public class UploadController {
	@Autowired
	private UploadService uploadService;
	/**
	 * 上传图片
	 * 返回图片URL地址 以json数据格式
	 * @param pic 图片
	 * @param response 
	 */
	@RequestMapping(value="/upload/uploadPic.do")
	public void uploadPic(@RequestParam(required = false) MultipartFile pic
			,HttpServletResponse response) throws IOException{
		
		String path =  uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		
		String url = Constants.IMG_URL+path;
		
		JSONObject jo = new JSONObject();
		jo.put("url", url);
		
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jo.toString());
	}
	
	/**
	 * 上传多张图片
	 * 返回图片URL地址的List 以json数据格式
	 * @param pic 图片组
	 */
	@RequestMapping(value="/upload/uploadPics.do")
	public @ResponseBody List<String> uploadPics(@RequestParam(required = false) MultipartFile[] pics
			) throws IOException{
		List<String> urls = new ArrayList<>();
		
		for (MultipartFile pic : pics) {
			String path =  uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());			
			String url = Constants.IMG_URL+path;
			urls.add(url);
		}
		return urls;
		
	}
	
	/**
	 * 上传富文本格式图片
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/upload/uploadFck.do")
	public void uploadFck(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//无敌版接收
		//强转spring 的MultipartRequest
		MultipartRequest mr =  (MultipartRequest) request;
		Map<String, MultipartFile> fileMep = mr.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet = fileMep.entrySet(); 
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile pic = entry.getValue();
			
			String path =  uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			
			String url = Constants.IMG_URL+path;
			
			JSONObject jo = new JSONObject();
			jo.put("error", 0);
			jo.put("url", url);
			
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(jo.toString());
			
		}
	}
	
}
