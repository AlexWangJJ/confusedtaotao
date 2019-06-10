package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object hanlder,
			Exception exception) {
		//把日志写入到日志文件中，进行打印
		System.out.println(exception.getMessage());
		
		exception.printStackTrace();
		//通知开发人员(第三方接口)
		
		//用户友好提示
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/exception");//不需要后缀因为有视图解析器
		modelAndView.addObject("message", "你的网络存在异常，请刷新页面");
		return modelAndView;
	}

}
