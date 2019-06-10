package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {
	
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	
	@Autowired
	private SearchService service;
	

	@RequestMapping("/search")
	public String search(@RequestParam(defaultValue="1")Integer page,@RequestParam(value="q") String queryString,Model model) throws Exception{
		//引入属性文件中的变量值
		//注入服务
		//调用服务
		//get请求出现乱码需要进行处理
		queryString = new String(queryString.getBytes("iso-8859-1"),"utf-8");
		
		SearchResult result = service.search(queryString, page, ITEM_ROWS);
		
		
		model.addAttribute("query",queryString);
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("itemList",result.getItemList());
		model.addAttribute("page", page);
		
		//设置数据返回到jsp中
		return "search";
	}

}
