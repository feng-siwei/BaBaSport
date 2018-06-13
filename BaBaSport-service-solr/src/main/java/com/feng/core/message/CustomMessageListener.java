package com.feng.core.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.feng.core.service.SearchServuce;

/**
 * 监听器处理类
 * @author 冯思伟
 *
 */
public class CustomMessageListener implements MessageListener{

	@Autowired
	private SearchServuce searchServuce;
	@Override
	public void onMessage(Message message) {
		
		ActiveMQTextMessage am = (ActiveMQTextMessage) message;
		
		try {
			searchServuce.insertProductToSolr(Long.parseLong(am.getText()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
