package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;


/**
 * 
 * @author AlexWang
 * @version 1.0
 */
public interface ContentService{
	
	/**
	 * 插入内容
	 * @param content
	 * @return
	 */
	TaotaoResult saveContent(TbContent content);
	/**
	 * 根据内容分类id获取内容
	 * @param categoryId
	 * @return
	 */
	List<TbContent> getContentListByCatId(Long categoryId);
	
}