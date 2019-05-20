package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;


@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper mapper;
	
	
	@Override
	public TaotaoResult saveContent(TbContent content) {
		//注入mapper
		
		
		//补全其他属性
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());
		
		//插入内容表
		mapper.insertSelective(content);
		
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		
		TbContentExample example = new TbContentExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> list = mapper.selectByExample(example);
		
		
		return list;
	}


}
