package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
/**
 * 展示首页
 * @author AlexWang
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
	@RequestMapping("/index")
	public String showIndex(){
		return "index";
	}
}
