package com.taotao.item.pojo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.taotao.pojo.TbItem;

public class Item extends TbItem{
	
	public Item(TbItem tbItem){
		//this.setBarcode(tbItem.getBarcode());
		//数据拷贝到
		BeanUtils.copyProperties(tbItem, this);
		
	}
	
	public String[] getImages(){
		if (StringUtils.isNoneBlank(super.getImage())) {
			return super.getImage().split(",");
		}
		return null;
	}
	
	
}
