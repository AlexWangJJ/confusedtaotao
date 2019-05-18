package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public TaotaoResult createContentCategory(Long parentId, String name) {
		// 构建对象不全其他属性
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setCreated(new Date());
		contentCategory.setIsParent(false);
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setSortOrder(1);
		contentCategory.setUpdated(new Date());
		
		//插入数据
		contentCategoryMapper.insertSelective(contentCategory);
		//返回内容分类的id 需要逐渐返回
		
		//如果父亲是叶子结点需要改变状态
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(parentId);
		
		if (category.getIsParent()==false) {
			category.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKeySelective(category);//更新节点的属性为true
		}
		
		
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public List<EUTreeNode> getContentCategoryList(Long parentId) {
		// 1、取查询参数id，parentId
		// 2、根据parentId查询tb_content_category，查询子节点列表。
		TbContentCategoryExample example = new TbContentCategoryExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		// 3、得到List<TbContentCategory>
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		// 4、把列表转换成List<EasyUITreeNode>ub
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到列表
			resultList.add(node);
		}
		return resultList;
	}

}

