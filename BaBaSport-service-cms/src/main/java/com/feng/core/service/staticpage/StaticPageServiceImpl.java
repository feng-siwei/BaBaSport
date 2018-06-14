package com.feng.core.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 静态化
 * @author 冯思伟
 *
 */
public class StaticPageServiceImpl implements StaticPageService, ServletContextAware{
	//配置文件 注入
	private Configuration conf;

	public void setFreeMarkerConfigurert(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}
	

	//静态化商品
	//静态化 商品  ActiveMQ
	@Override
	public void productStaticPage(Map<String,Object> root,String id){
		//输出的路径  全路径
		String path = getPath("/html/product/" + id + ".html");	
		
		File f = new File(path);
		//目录创建
		File parentFile = f.getParentFile();
		if(!parentFile.exists()){      
			parentFile.mkdirs();
		}
		
		Writer out = null;
		try {
			//读文件  UTF-8
			Template template = conf.getTemplate("product.html");
			//输出  UTF-8
			out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
			//处理
			template.process(root, out); 
 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//获得路径
	public String getPath(String name) {
		return servletContext.getRealPath(name);
	}

	
	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}

}
