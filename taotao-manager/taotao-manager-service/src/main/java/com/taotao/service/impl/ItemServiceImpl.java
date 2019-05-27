package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.manager.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	@Autowired
	private JmsTemplate jmsTemplate;
	// 这里如果设置成按照名称进行
	@Autowired
	private Destination topicDestination;
	@Autowired
	private TbItemMapper mapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JedisClient client;

	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 30;
		}

		PageHelper.startPage(page, rows);

		TbItemExample example = new TbItemExample();

		List<TbItem> list = mapper.selectByExample(example);

		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) pageInfo.getTotal());
		result.setRows(pageInfo.getList());

		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		// 先生成商品id
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入到商品表
		mapper.insert(item);
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述
		itemDescMapper.insert(itemDesc);

		// 发送一个商品添加消息
		jmsTemplate.send(topicDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}

		});

		return TaotaoResult.ok();
	}

	@Override
	public TbItem getItemById(Long itemId) {

		/*
		 * TbItemExample example = new TbItemExample();
		 * 
		 * Criteria criteria = example.createCriteria();
		 * 
		 * criteria.andIdEqualTo(itemId); List<TbItem> itemList =
		 * mapper.selectByExample(example);
		 * 
		 * return itemList.get(0);
		 */

		// 添加缓存

		// 1.从缓存中获取数据，如果有直接返回
		try {
			String jsonstr = client.get(ITEM_INFO_KEY + ":" + itemId + ":BASE");

			if (StringUtils.isNotBlank(jsonstr)) {
				// 重新设置商品的有效期
				client.expire(ITEM_INFO_KEY + ":" + itemId + ":BASE", ITEM_INFO_KEY_EXPIRE);
				return JsonUtils.jsonToPojo(jsonstr, TbItem.class);

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 2如果没有数据

		// 注入mapper
		// 调用方法
		TbItem tbItem = mapper.selectByPrimaryKey(itemId);
		// 返回tbitem

		// 3.添加缓存到redis数据库中
		// 注入jedisclient
		// ITEM_INFO:123456:BASE
		// ITEM_INFO:123456:DESC
		try {
			client.set(ITEM_INFO_KEY + ":" + itemId + ":BASE", JsonUtils.objectToJson(tbItem));
			// 设置缓存的有效期
			client.expire(ITEM_INFO_KEY + ":" + itemId + ":BASE", ITEM_INFO_KEY_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		// 添加缓存

		// 1.从缓存中获取数据，如果有直接返回
		try {
			String jsonstr = client.get(ITEM_INFO_KEY + ":" + itemId + ":DESC");

			if (StringUtils.isNotBlank(jsonstr)) {
				// 重新设置商品的有效期
				System.out.println("有缓存");
				client.expire(ITEM_INFO_KEY + ":" + itemId + ":DESC", ITEM_INFO_KEY_EXPIRE);
				return JsonUtils.jsonToPojo(jsonstr, TbItemDesc.class);

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 如果没有查到数据 从数据库中查询
		TbItemDesc itemdesc = itemDescMapper.selectByPrimaryKey(itemId);
		// 添加缓存
		// 3.添加缓存到redis数据库中
		// 注入jedisclient
		// ITEM_INFO:123456:BASE
		// ITEM_INFO:123456:DESC
		try {
			client.set(ITEM_INFO_KEY + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemdesc));
			// 设置缓存的有效期
			client.expire(ITEM_INFO_KEY + ":" + itemId + ":DESC", ITEM_INFO_KEY_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemdesc;
	}

}
