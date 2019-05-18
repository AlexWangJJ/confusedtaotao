package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	//查询商品列表
	public EasyUIDataGridResult getItemList(Integer page ,Integer rows);
	
	
	TaotaoResult addItem(TbItem item,String desc);
}
