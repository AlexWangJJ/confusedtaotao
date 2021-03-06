package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemCatService;
import com.taotao.service.ItemService;

/**
 * 商品分类管理
 * 
 * @author AlexWang
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EUTreeNode> getCatList(Long parentId) {
		// 创建查询条件

		TbItemCatExample example = new TbItemCatExample();

		Criteria criteria = example.createCriteria();

		criteria.andParentIdEqualTo(parentId);

		// 根据条件查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);

		List<EUTreeNode> result = new ArrayList<>();
		// 转换成treenode列表
		for (TbItemCat cat : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(cat.getId());
			node.setText(cat.getName());
			node.setState(cat.getIsParent() ? "closed" : "open");
			result.add(node);
		}

		return result;
	}

}
