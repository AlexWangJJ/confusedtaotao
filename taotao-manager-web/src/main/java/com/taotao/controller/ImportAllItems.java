package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;

@Controller
public class ImportAllItems {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/index/importAll", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult importAll() throws Exception {

		return searchService.importAllSearchItems();
	}
}
