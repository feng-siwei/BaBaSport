package com.feng.core.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.feng.core.bean.product.Color;
import com.feng.core.bean.product.Product;
import com.feng.core.bean.product.Sku;
import com.feng.core.service.CmsService;
import com.feng.core.service.staticpage.StaticPageService;

/**
 * 监听器处理类
 * @author 冯思伟
 *
 */
public class CustomMessageListener implements MessageListener{

	@Autowired
	private StaticPageService staticPageService;
	@Autowired
	private CmsService cmsService;
	@Override
	public void onMessage(Message message) {
		
		ActiveMQTextMessage am = (ActiveMQTextMessage) message;
		
		try {
			String id = am.getText();
			//数据
			Map<String, Object>root = new HashMap<>();
			//商品
			Product product = cmsService.selectProductById(Long.parseLong(id));
			//sku
			List<Sku> skus = cmsService.selectSkuListByProductId(Long.parseLong(id));
			//颜色集合
			Set<Color> colors = new HashSet<>();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			
			root.put("product", product);
			root.put("skus", skus);
			root.put("colors", colors);
			
			//生成
			staticPageService.productStaticPage(root, id);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
