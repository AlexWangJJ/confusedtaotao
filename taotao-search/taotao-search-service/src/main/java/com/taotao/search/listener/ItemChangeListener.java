package com.taotao.search.listener;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.service.SearchService;

public class ItemChangeListener implements MessageListener {

	@Autowired
	private SearchService service;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = null;
			Long itemId = null;
			// 取商品id
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				itemId = Long.parseLong(textMessage.getText());
			}
			// 向索引库添加文档
			service.updateItemById(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}