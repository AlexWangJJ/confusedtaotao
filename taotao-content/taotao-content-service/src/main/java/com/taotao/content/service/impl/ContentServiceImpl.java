package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

/*	// 注入redis
	@Autowired
	private JedisClient jedisClient;*/

	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;

	@Autowired
	private TbContentMapper mapper;

	@Override
	public TaotaoResult saveContent(TbContent content) {
		// 注入mapper

		// 补全其他属性
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());

		// 插入内容表
		mapper.insertSelective(content);
		
/*		//缓存同步
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());*/

		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {

		// 查询缓存
/*		try {
			String json = jedisClient.hget(CONTENT_KEY, categoryId + "");
			// 判断json是否为空
			if (StringUtils.isNotBlank(json)) {
				// 把json转换成list
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		TbContentExample example = new TbContentExample();

		Criteria criteria = example.createCriteria();

		criteria.andCategoryIdEqualTo(categoryId);

		List<TbContent> list = mapper.selectByExample(example);
		// 向缓存中添加数据
/*		try {
			jedisClient.hset(CONTENT_KEY, categoryId + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		return list;
	}

}
