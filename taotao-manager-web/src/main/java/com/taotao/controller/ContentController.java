package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService service;
	
	
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	
	public TaotaoResult saveContent(TbContent content){
		
		return service.saveContent(content);	
		
	}
}
