package com.taotao.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
/**
 * 内容分类前端内容
 * @author AlexWang
 *
 */
@Controller
public class ContentCategoryController {
	//这里忘记之后会直接报空指针异常
	@Autowired
	private ContentCategoryService service;
	/**
	 * 生成目录树
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value="/content/category/list",method=RequestMethod.GET)
	@ResponseBody
	public List<EUTreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0")Long parentId){
		//引入服务
		//注入
		//调用
		List<EUTreeNode> list = service.getContentCategoryList(parentId);
		return list;
	}
	
	/**
	 * 添加目录
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId,String name){
		return service.createContentCategory(parentId, name);
		
		
	}
	/**
	 * 对目录节点进行rename
	 * @param nodeId
	 * @param name
	 * @return
	 */
	
	@RequestMapping(value="/content/category/update",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateContentCategory(@RequestParam(value="id")Long nodeId,String name){
		
		return service.updateContentCategory(nodeId, name);
		
	}
	/**
	 * 删除节点
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value="/content/category/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContentCategory(@RequestParam(value="id")Long nodeId){
		return service.deleteContentCategory(nodeId);
	}
}
