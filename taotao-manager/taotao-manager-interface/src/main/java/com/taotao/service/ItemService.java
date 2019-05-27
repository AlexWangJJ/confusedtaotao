package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	//查询商品列表
	public EasyUIDataGridResult getItemList(Integer page ,Integer rows);
	//查询单个商品的详情
	/**
	 * 通过id获取商品
	 * @param itemId
	 * @return
	 */
	public TbItem getItemById(Long itemId);
	/**
	 * 添加商品及其描述
	 * @param item
	 * @param desc
	 * @return
	 */
	TaotaoResult addItem(TbItem item,String desc);
	
	/**
	 * 通过id获取商品的描述
	 * @param itemId
	 * @return
	 */
	TbItemDesc getItemDescById(Long itemId);
}
