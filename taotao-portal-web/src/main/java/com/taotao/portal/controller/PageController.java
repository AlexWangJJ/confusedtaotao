package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;

/**
 * 展示首页
 * 
 * @author AlexWang
 *
 */
@Controller
public class PageController {

	@Autowired
	private ContentService service;
	
	//用属性文件的形式对id进行注入
	@Value("${AD1_CATEGORY_ID}")
	private Long categoryId;
	@Value("${AD1_HEIGHT_B}")
	private String AD1_HEIGHT_B;
	@Value("${AD1_HEIGHT}")
	private String AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private String AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private String AD1_WIDTH_B;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		//引入服务
		//注入服务
		//添加业务逻辑，根据分类内容id，查询内容列表
		List<TbContent> list = service.getContentListByCatId(categoryId);
		//转换成自定义的pojo
		List<Ad1Node> result = new ArrayList<>();
		for(TbContent content:list){
			Ad1Node node = new Ad1Node();
			node.setAlt(content.getSubTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setHref(content.getUrl());
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setSrc(content.getPic());
			node.setSrcB(content.getPic2());
			result.add(node);
		}
		model.addAttribute("ad1",JsonUtils.objectToJson(result));
		//传递给jsp生成动态页面
		return "index";
	}
}
