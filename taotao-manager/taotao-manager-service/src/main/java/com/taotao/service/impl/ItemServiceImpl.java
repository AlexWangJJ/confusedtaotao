package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper mapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		if (page==null) {
			page = 1;
		}
		if (rows==null) {
			rows = 30;
		}
		
		PageHelper.startPage(page, rows);
		
		TbItemExample example = new TbItemExample();
		
		List<TbItem> list = mapper.selectByExample(example);
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int)pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		
		return result;
	}
	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		// 先生成商品id
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        // 商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        // 插入到商品表
        mapper.insert(item);
        // 商品描述
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 插入商品描述
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
	}

}