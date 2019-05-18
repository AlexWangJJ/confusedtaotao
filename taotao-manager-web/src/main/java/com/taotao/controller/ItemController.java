package com.taotao.controller;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/item/list",method = RequestMethod.GET)
	@ResponseBody
	
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping("item/save")
	@ResponseBody
	public TaotaoResult addItem(TbItem item,String desc){
		
		return itemService.addItem(item, desc);
	}

}
