package com.taotao.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.content.service.ContentCategoryService;

@Controller
public class ContentCategoryController {
	
	private ContentCategoryService Service;
	
	@RequestMapping(value="content/category/list",method=RequestMethod.GET)
	@ResponseBody
	public List<EUTreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0")Long parentId){
		//引入服务
		//注入
		//调用
		return Service.getContentCategoryList(parentId);
		
	}
}
